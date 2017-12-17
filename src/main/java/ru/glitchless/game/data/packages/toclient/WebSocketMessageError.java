package ru.glitchless.game.data.packages.toclient;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.glitchless.newserver.data.model.WebSocketMessage;

public class WebSocketMessageError extends WebSocketMessage {
    private final String type;
    private String reason;

    public WebSocketMessageError() {
        this.type = "WebSocketMessageError";
    }

    public WebSocketMessageError(@JsonProperty("type") String type) {
        this.type = type;
    }

    public String getReason() {
        return reason;
    }

    public WebSocketMessageError setReason(String inReason) {
        this.reason = inReason;
        return this;
    }
}
