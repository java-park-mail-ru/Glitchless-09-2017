package ru.glitchless.game.data.physics;

import ru.glitchless.game.collision.data.Arc;
import ru.glitchless.game.collision.data.Circle;
import ru.glitchless.game.collision.data.CollisionPoint;
import ru.glitchless.game.data.Point;
import ru.glitchless.game.data.physics.base.PhysicObject;
import ru.glitchless.game.network.CollisionHandler;
import ru.glitchless.game.utils.Utils;
import ru.glitchless.newserver.data.model.WebSocketUser;
import ru.glitchless.newserver.utils.Constants;

public class HealthBlock extends PhysicObject implements ICanGetArc {
    public static final float MAGIC_NUMBER2 = 2.15f;
    public static final int HEALTH_SIZE = 252;
    private static final float MAGIC_NUMBER = 0.15f;
    private Circle circle;
    private Arc collisionArc;
    private WebSocketUser aligmentPlayer;

    public HealthBlock(Point point, Circle circle, WebSocketUser webSocketUser) {
        super(point);
        this.circle = circle;
        this.aligmentPlayer = webSocketUser;
    }

    @Override
    public Arc getArc() {

        final CollisionPoint coord = this.getPoint().toCollisionPoint();
        final float rotation = (float) Utils.radians(this.getRotation());
        final double lengthHypotenuse = HEALTH_SIZE / 2; //empiric coefficient

        final float deltaXLeft = (float) (lengthHypotenuse * Math.cos(rotation + MAGIC_NUMBER));
        final float deltaYLeft = (float) (lengthHypotenuse * Math.sin(rotation + MAGIC_NUMBER));
        final float deltaXRight = (float) (lengthHypotenuse * Math.cos(rotation - MAGIC_NUMBER));
        final float deltaYRight = (float) (lengthHypotenuse * Math.sin(rotation - MAGIC_NUMBER));

        final CollisionPoint pointLeft = new CollisionPoint(coord.getX() - deltaXLeft,
                coord.getY() - deltaYLeft);
        final CollisionPoint pointRight = new CollisionPoint(coord.getX() + deltaXRight,
                coord.getY() + deltaYRight);

        return Arc.Companion.fromPoints(pointLeft, pointRight, coord);
    }

    public void refreshCollisionArc() {
        this.collisionArc = null;
        this.collisionArc = getArc();
    }

    @Override
    public void setRotation(float rotation) {
        super.setRotation(rotation);

        final float radius = (float) (this.circle.getRadius() - Constants.GAME_PLATFORM_SIZE / 4);
        final float rotationRadian = rotation / Constants.GAME_ROTATION_COEFFICIENT;
        final float deltaX = (float) (radius * Math.sin(rotationRadian));
        final float deltaY = (float) (radius * Math.cos(rotationRadian));

        final CollisionPoint tmp = this.circle.getCenter();
        final Point newPoint = new Point(tmp.getX() - deltaX, tmp.getY() + deltaY);

        this.setPoint(newPoint);

        this.refreshCollisionArc();
    }

    public void onCollision(Object[] points, CollisionHandler collisionHandler) {
        this.setForDestroy(true);
        collisionHandler.onDestroyObject(this);
        collisionHandler.onHpLoss(aligmentPlayer);
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    @Override
    public void setForDestroy(boolean forDestroy) {
        super.setForDestroy(forDestroy);
    }
}
