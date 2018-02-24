package ru.glitchless.game.data.physics;

import ru.glitchless.game.collision.data.Arc;
import ru.glitchless.game.collision.data.CollisionPoint;
import ru.glitchless.game.data.Point;
import ru.glitchless.game.data.packages.fromclient.ClientCommitMessage;
import ru.glitchless.game.data.physics.base.PhysicEntity;
import ru.glitchless.game.utils.Utils;
import ru.glitchless.newserver.data.model.WebSocketUser;
import ru.glitchless.newserver.utils.Constants;

public class Platform extends PhysicEntity implements ICanGetArc {
    public static final float MAGIC_CONSTANT = 0.5f;
    private final WebSocketUser platformUser;
    private final Kirkle circle;

    public Platform(Point point, WebSocketUser webSocketUser, Kirkle circle) {
        super(point);
        this.platformUser = webSocketUser;
        this.circle = circle;
    }

    @Override
    public void setRotation(float rotation) {
        if (rotation < 0) {
            rotation = Constants.CIRCLE_ANGEL + rotation;
        }

        rotation = rotation % Constants.CIRCLE_ANGEL;

        super.setRotation(rotation);
        final float radius = circle.getRadius() - Constants.GAME_RADIUS_PLATFORM_PADDING;
        final float rotationRadian = rotation / Constants.GAME_ROTATION_COEFFICIENT;
        final float deltaX = (float) (radius * Math.sin(rotationRadian));
        final float deltaY = (float) (radius * Math.cos(rotationRadian));

        this.setPoint(new Point(circle.getPoint().getPosX() - deltaX,
                circle.getPoint().getPosY() + deltaY));
    }

    public boolean valid(WebSocketUser user, ClientCommitMessage clientCommitMessage) {
        if (!user.equals(platformUser)) {
            return false;
        }

        // TODO check speed
        return true;
    }

    @Override
    public Arc getArc() {
        final Point coord = this.getPoint();
        final float rotation = (float) Utils.radians(this.getRotation());
        final float angle = MAGIC_CONSTANT;
        final float lengthHypotenuse = Constants.GAME_PLATFORM_SIZE / 2;

        final float deltaXLeft = (float) (lengthHypotenuse * Math.cos(rotation + angle));
        final float deltaYLeft = (float) (lengthHypotenuse * Math.sin(rotation + angle));
        final float deltaXRight = (float) (lengthHypotenuse * Math.cos(rotation - angle));
        final float deltaYRight = (float) (lengthHypotenuse * Math.sin(rotation - angle));
        final float centralX = (float) (coord.getPosX() - (lengthHypotenuse / 4) * Math.sin(rotation));
        final float centralY = (float) (coord.getPosY() + (lengthHypotenuse / 4) * Math.cos(rotation));

        final CollisionPoint pointLeft = new CollisionPoint(centralX - deltaXLeft,
                centralY - deltaYLeft);
        final CollisionPoint pointRight = new CollisionPoint(centralX + deltaXRight,
                centralY + deltaYRight);
        final CollisionPoint pointCentral = new CollisionPoint(centralX, centralY);

        return Arc.Companion.fromPoints(pointLeft, pointRight, pointCentral);
    }

    public WebSocketUser getPlatformUser() {
        return platformUser;
    }
}
