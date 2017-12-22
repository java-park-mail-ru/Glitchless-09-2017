package ru.glitchless.game.data.physics;

import ru.glitchless.game.collision.data.Arc;
import ru.glitchless.game.collision.data.Circle;
import ru.glitchless.game.collision.data.CollisionPoint;
import ru.glitchless.game.data.Point;
import ru.glitchless.game.data.physics.base.PhysicObject;
import ru.glitchless.newserver.utils.Constants;

public class ForceField extends PhysicObject implements ICanGetArc {
    public static final float MAGIC_NUMBER2 = 2.15f;
    private static final float MAGIC_NUMBER = 0.79f;
    private Circle circle;
    private Arc collisionArc;
    private boolean off = false;
    private float shieldStatus = Constants.MAX_SHIELD;

    public ForceField(Point point, Circle circle) {
        super(point);
        this.circle = circle;
        this.collisionArc = getArc();
    }

    public Arc getArc() {
        if (this.collisionArc != null) {
            return this.collisionArc;
        }

        final CollisionPoint coord = this.getPoint().toCollisionPoint();
        final float rotation = this.getRotation();
        final double lengthHypotenuse = Math.sqrt(Math.pow(Constants.GAME_FIELD_SIZE.getPosX(), 2)
                + Math.pow(Constants.GAME_FIELD_SIZE.getPosY(), 2)) / MAGIC_NUMBER2; //empiric coefficient

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

    public float getShieldStatus() {
        return shieldStatus;
    }

    public void addShieldStatus(float increase) {
        final float newShield = shieldStatus + increase;
        this.shieldStatus = Math.max(Math.min(newShield, Constants.MAX_SHIELD), 0);

        this.off = (shieldStatus / Constants.MAX_SHIELD) < Constants.SHIELD_ACTIVATION_PERCENT;
    }


    public boolean isOff() {
        return off;
    }

    public void onCollision(Object[] objects) {
        addShieldStatus(-Constants.LASER_DAMAGE);
    }
}
