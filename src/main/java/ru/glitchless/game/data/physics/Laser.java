package ru.glitchless.game.data.physics;

import ru.glitchless.game.collision.data.CollisionPoint;
import ru.glitchless.game.data.Point;
import ru.glitchless.game.data.Vector;
import ru.glitchless.game.data.physics.base.PhysicEntity;
import ru.glitchless.game.utils.Utils;

public class Laser extends PhysicEntity {
    public Laser(Point point) {
        super(point);
    }

    @Override
    public void setSpeed(Vector speed) {
        super.setSpeed(speed);
        this.setRotation((float) Utils.degrees(Math.atan2(speed.getDiffY(), speed.getDiffX())));
    }

    public void onCollision(CollisionPoint[] points, Platform platform) {

    }
}
