package ru.glitchless.game.data.packages.fromclient;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.glitchless.newserver.data.model.WebSocketMessage;

public class WantPlayMessage extends WebSocketMessage {
    private int state;

    public WantPlayMessage(@JsonProperty("type") String type) {
        String type1 = type;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
