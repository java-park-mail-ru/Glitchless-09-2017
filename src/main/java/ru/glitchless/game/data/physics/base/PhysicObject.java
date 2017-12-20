package ru.glitchless.game.data.physics.base;

import ru.glitchless.game.data.Point;

import java.util.ArrayList;
import java.util.List;

public class PhysicObject {
    private Point point;
    private float rotation = 0;
    private boolean forDestroy = false;
    private int objectId = -1;
    private List<IDestroyListener> destroyListeners = new ArrayList<>();

    public PhysicObject(Point point) {
        this.point = point;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public boolean isForDestroy() {
        return forDestroy;
    }

    public void setForDestroy(boolean forDestroy) {
        this.forDestroy = forDestroy;
    }

    public int getObjectId() {
        return objectId;
    }

    public void setObjectId(int objectId) {
        this.objectId = objectId;
    }

    public void destroy() {
        this.destroyListeners.forEach((item) -> {
            item.onDestroy(this);
        });
    }

    public void subscribeOnDestroy(IDestroyListener destroyListener) {
        this.destroyListeners.add(destroyListener);
    }
}
