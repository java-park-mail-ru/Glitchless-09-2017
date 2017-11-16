package ru.glitchless.game.data.packages.toclient;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.glitchless.game.data.Point;
import ru.glitchless.game.data.Vector;
import ru.glitchless.server.data.models.WebSocketMessage;

public class ServerSnapMessage extends WebSocketMessage {
    private String type;
    private int objectId;
    @JsonProperty("diff")
    private Vector vector;
    @JsonProperty("coord")
    private Point point;

    public ServerSnapMessage() {
        this.type = "ServerSnapMessage";
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
}
