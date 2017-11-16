package ru.glitchless.game.data.packages.toclient;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.glitchless.game.data.Point;
import ru.glitchless.server.data.models.WebSocketMessage;

public class LightServerSnapMessage extends WebSocketMessage {
    private String type;
    private int objectId;
    private int commitId;
    @JsonProperty("coord")
    private Point point;

    public LightServerSnapMessage() {
        this.type = "ServerSnapMessage";
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
}
