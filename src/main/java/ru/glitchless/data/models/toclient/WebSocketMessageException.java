package ru.glitchless.data.models.toclient;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.glitchless.data.models.WebSocketMessage;

public class WebSocketMessageException extends WebSocketMessage {
    private String type;
    private String reason;

    public WebSocketMessageException() {
        this.type = "WebSocketMessageException";
    }

    public WebSocketMessageException(@JsonProperty("type") String type) {
        this.type = type;
    }

    public String getReason() {
        return reason;
    }

    public WebSocketMessageException setReason(String inReason) {
        this.reason = inReason;
        return this;
    }
}
