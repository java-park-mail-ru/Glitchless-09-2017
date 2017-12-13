package ru.glitchless.game.data.packages.fromclient;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.glitchless.newserver.data.model.WebSocketMessage;

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
