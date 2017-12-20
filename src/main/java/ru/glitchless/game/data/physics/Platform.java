package ru.glitchless.game.data.physics;

import ru.glitchless.game.data.Point;
import ru.glitchless.game.data.packages.fromclient.ClientCommitMessage;
import ru.glitchless.game.data.physics.base.PhysicEntity;
import ru.glitchless.newserver.data.model.WebSocketUser;
import ru.glitchless.newserver.utils.Constants;

public class Platform extends PhysicEntity {
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

        this.setPoint(circle
                .getPoint()
                .plus(new Point(deltaX, deltaY))
        );
    }

    public boolean valid(WebSocketUser user, ClientCommitMessage clientCommitMessage) {
        if (!user.equals(platformUser)) {
            return false;
        }

        // TODO check speed
        return true;
    }
}
