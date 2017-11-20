package ru.glitchless.game.data.physics;

import ru.glitchless.game.data.Point;
import ru.glitchless.game.data.exceptions.GameException;
import ru.glitchless.game.data.packages.fromclient.ClientCommitMessage;
import ru.glitchless.game.data.physics.base.PhysicEntity;
import ru.glitchless.server.data.models.WebSocketUser;
import ru.glitchless.server.utils.Constants;

public class Platform extends PhysicEntity {
    private WebSocketUser platformUser;
    private Kirkle circle;

    public Platform(Point point, int objectId, WebSocketUser webSocketUser, Kirkle circle) {
        super(point, objectId);
        this.platformUser = webSocketUser;
        this.circle = circle;
    }

    @Override
    public void setRotation(float rotation) {
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
        if(!user.equals(platformUser)){
            return false;
        }

        // TODO check speed
        return true;
    }
}
