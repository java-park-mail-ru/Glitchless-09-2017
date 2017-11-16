package ru.glitchless.server.data.models.game.toclient;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.glitchless.server.data.models.WebSocketMessage;

public class WebSocketMessageError extends WebSocketMessage {
    private String type;
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
