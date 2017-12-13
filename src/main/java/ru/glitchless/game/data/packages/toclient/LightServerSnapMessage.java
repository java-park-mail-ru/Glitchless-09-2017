package ru.glitchless.game.data.packages.toclient;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.glitchless.game.data.Point;
import ru.glitchless.game.data.packages.fromclient.ClientCommitMessage;
import ru.glitchless.game.data.physics.base.PhysicObject;
import ru.glitchless.newserver.data.model.WebSocketMessage;

public class LightServerSnapMessage extends WebSocketMessage {
    private String type;
    private int objectId;
    private int commitId;
    @JsonProperty("coord")
    private Point point;
    private float rotation;

    public LightServerSnapMessage() {
        this.type = "LightServerSnapMessage";
    }

    public LightServerSnapMessage(ClientCommitMessage commitMessage) {
        this();
        this.commitId = commitMessage.getCommitNumber();
        this.objectId = commitMessage.getObjectId();
    }

    public LightServerSnapMessage(PhysicObject physicObject, ClientCommitMessage commitMessage) {
        this(commitMessage);
        this.point = physicObject.getPoint();
        this.rotation = physicObject.getRotation();
    }

    public int getObjectId() {
        return objectId;
    }

    public void setObjectId(int objectId) {
        this.objectId = objectId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public int getCommitId() {
        return commitId;
    }

    public void setCommitId(int commitId) {
        this.commitId = commitId;
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }
}
