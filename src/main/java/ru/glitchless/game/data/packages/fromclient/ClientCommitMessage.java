package ru.glitchless.game.data.packages.fromclient;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.glitchless.game.data.Vector;
import ru.glitchless.server.data.models.WebSocketMessage;

public class ClientCommitMessage extends WebSocketMessage {
    private String type;
    private int commitNumber = 0;
    private int objectId;
    @JsonProperty("speed")
    private Vector vector;

    public ClientCommitMessage(@JsonProperty("type") String type) {
        this.type = type;
    }

    public int getCommitNumber() {
        return commitNumber;
    }

    public void setCommitNumber(int commitNumber) {
        this.commitNumber = commitNumber;
    }

    public int getObjectId() {
        return objectId;
    }

    public void setObjectId(int objectId) {
        this.objectId = objectId;
    }

    @JsonProperty("speed")
    public Vector getVector() {
        return vector;
    }

    @JsonProperty("speed")
    public void setVector(Vector vector) {
        this.vector = vector;
    }
}
