package ru.glitchless.game.data.packages.toclient;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.glitchless.game.data.Point;
import ru.glitchless.game.data.Vector;
import ru.glitchless.game.data.physics.base.PhysicEntity;
import ru.glitchless.game.data.physics.base.PhysicObject;

public class SnapObject {
    @JsonProperty("objectId")
    private int objectId;
    @JsonProperty("coord")
    private Point coord;
    @JsonProperty("data")
    private Object additionalInfo;
    @JsonProperty("speed")
    private Vector speed;
    @JsonProperty("rotation")
    private float rotation;
    @JsonProperty("rotationspeed")
    private float rotationSpeed;

    public SnapObject(PhysicObject object) {
        this.objectId = object.getObjectId();
        this.coord = object.getPoint();
        this.rotation = object.getRotation();
    }

    public SnapObject(PhysicEntity entity) {
        this((PhysicObject) entity);
        this.speed = entity.getSpeed();
        this.rotationSpeed = entity.getRotationSpeed();
    }

    public int getObjectId() {
        return objectId;
    }

    public Point getCoord() {
        return coord;
    }

    public Object getAdditionalInfo() {
        return additionalInfo;
    }

    public SnapObject setAdditionalInfo(Object addInfo) {
        this.additionalInfo = addInfo;
        return this;
    }

    public Vector getSpeed() {
        return speed;
    }

    public float getRotation() {
        return rotation;
    }
}
