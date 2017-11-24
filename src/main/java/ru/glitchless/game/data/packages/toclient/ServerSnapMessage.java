package ru.glitchless.game.data.packages.toclient;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.glitchless.game.data.Point;
import ru.glitchless.game.data.Vector;
import ru.glitchless.game.data.physics.base.PhysicEntity;
import ru.glitchless.game.data.physics.base.PhysicObject;
import ru.glitchless.server.data.models.WebSocketMessage;

public class ServerSnapMessage extends WebSocketMessage {
    private String type;
    private int objectId;
    @JsonProperty("speed")
    private Vector vector;
    private float rotationSpeed;
    @JsonProperty("coord")
    private Point point;
    private float rotation;

    public ServerSnapMessage() {
        this.type = "ServerSnapMessage";
    }

    public ServerSnapMessage(PhysicObject physicObject) {
        this();
        this.objectId = physicObject.getObjectId();
        this.point = physicObject.getPoint();
        this.rotation = physicObject.getRotation();
    }

    public ServerSnapMessage(PhysicEntity physicEntity) {
        this((PhysicObject) physicEntity);
        this.vector = physicEntity.getSpeed();
        this.rotationSpeed = physicEntity.getRotationSpeed();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getObjectId() {
        return objectId;
    }

    public void setObjectId(int objectId) {
        this.objectId = objectId;
    }

    public Vector getVector() {
        return vector;
    }

    public void setVector(Vector vector) {
        this.vector = vector;
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

    public float getRotationSpeed() {
        return rotationSpeed;
    }

    public void setRotationSpeed(float rotationSpeed) {
        this.rotationSpeed = rotationSpeed;
    }
}
