package ru.glitchless.game.data.packages.toclient;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.glitchless.game.data.Point;
import ru.glitchless.game.data.physics.base.PhysicObject;

public class SnapObject {
    @JsonProperty("objectId")
    private int objectId;
    @JsonProperty("coord")
    private Point coord;
    @JsonProperty("data")
    private Object additionalInfo;

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

    public Object getAdditionalInfo() {
        return additionalInfo;
    }

    public SnapObject setAdditionalInfo(Object additionalInfo) {
        this.additionalInfo = additionalInfo;
        return this;
    }
}
