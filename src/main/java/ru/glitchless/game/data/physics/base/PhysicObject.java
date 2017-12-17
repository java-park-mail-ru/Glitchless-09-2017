package ru.glitchless.game.data.physics.base;

import ru.glitchless.game.data.Point;

public class PhysicObject {
    private Point point;
    private float rotation = 0;
    private boolean forDestroy = false;
    private int objectId = 0;

    public PhysicObject(Point point, int objectId) {
        this.point = point;
        this.objectId = objectId;
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
}
