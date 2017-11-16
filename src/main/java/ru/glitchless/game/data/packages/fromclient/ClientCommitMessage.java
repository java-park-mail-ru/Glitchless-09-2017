package ru.glitchless.game.data.packages.fromclient;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.glitchless.game.data.Vector;
import ru.glitchless.server.data.models.WebSocketMessage;

public class ClientCommitMessage extends WebSocketMessage {
    private String type;
    private int objectId;
    @JsonProperty("diff")
    private Vector vector;

    public ClientCommitMessage(@JsonProperty("type") String type) {
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
}
