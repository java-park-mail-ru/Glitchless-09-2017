package ru.glitchless.data.models.game.fromclient;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.glitchless.data.models.WebSocketMessage;

public class WantPlayMessage extends WebSocketMessage {
    private String type;
    private int state;

    public WantPlayMessage(@JsonProperty("type") String type) {
        this.type = type;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
