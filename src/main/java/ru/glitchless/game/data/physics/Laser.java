package ru.glitchless.game.data.physics;

import ru.glitchless.game.collision.data.CollisionPoint;
import ru.glitchless.game.data.Point;
import ru.glitchless.game.data.Vector;
import ru.glitchless.game.data.physics.base.PhysicEntity;
import ru.glitchless.game.utils.Utils;
import ru.glitchless.newserver.data.model.WebSocketUser;

public class Laser extends PhysicEntity {
    private boolean reflected = false;
    private WebSocketUser lastReflectedBy = null;

    public Laser(Point point) {
        super(point);
    }

    @Override
    public void setSpeed(Vector speed) {
        super.setSpeed(speed);
        this.setRotation((float) Utils.degrees(Math.atan2(speed.getDiffY(), speed.getDiffX())));

    }

    public void onCollision(CollisionPoint[] points, Platform platform) {
        final Vector vector = new Vector(points[1].getX(), points[1].getY());
        this.setSpeed(vector);
        this.reflected = true;
        this.lastReflectedBy = platform.getPlatformUser();
    }

    @Override
    public void setPoint(Point point) {
        super.setPoint(point);
    }

    public boolean isReflected() {
        return reflected;
    }

    public WebSocketUser getLastReflectedBy() {
        return lastReflectedBy;
    }

    @Override
    public void setForDestroy(boolean forDestroy) {
        super.setForDestroy(forDestroy);
    }
}
