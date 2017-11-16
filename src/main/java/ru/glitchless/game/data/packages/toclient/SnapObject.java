package ru.glitchless.game.data.packages.toclient;

import ru.glitchless.game.data.Point;
import ru.glitchless.game.data.physics.base.PhysicObject;

public class SnapObject {
    private int objectId;
    private Point coord;

    public SnapObject(PhysicObject object) {
        this.objectId = object.getObjectId();
        this.coord = object.getPoint();
    }

    public int getObjectId() {
        return objectId;
    }

    public void setObjectId(int objectId) {
        this.objectId = objectId;
    }

    public Point getCoord() {
        return coord;
    }

    public void setCoord(Point coord) {
        this.coord = coord;
    }
}
