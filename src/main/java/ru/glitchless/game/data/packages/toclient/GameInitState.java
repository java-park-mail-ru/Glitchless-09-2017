package ru.glitchless.game.data.packages.toclient;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.glitchless.newserver.data.model.WebSocketMessage;

public class GameInitState extends WebSocketMessage {
    private String type;
    private int state;

    public GameInitState() {
        this.type = "GameInitState";
    }

    public GameInitState(@JsonProperty("type") String type) {
        this.type = type;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
