package ru.glitchless.data.models.toclient;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.glitchless.data.models.WebSocketMessage;

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
