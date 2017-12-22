package ru.glitchless.game.data.packages.toclient;

import ru.glitchless.game.data.physics.base.PhysicEntity;
import ru.glitchless.game.data.physics.base.PhysicObject;
import ru.glitchless.newserver.data.model.WebSocketMessage;

public class ServerSnapMessage extends WebSocketMessage {
    private String type;
    private int objectId;
    private float rotationSpeed;
    private float rotation;

    public ServerSnapMessage() {
        this.type = "ServerSnapMessage";
    }

    public ServerSnapMessage(PhysicObject physicObject) {
        this();
        this.objectId = physicObject.getObjectId();
        this.rotation = physicObject.getRotation();
    }

    public ServerSnapMessage(PhysicEntity physicEntity) {
        this((PhysicObject) physicEntity);
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
