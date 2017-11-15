package ru.glitchless.data.models;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import ru.glitchless.data.models.fromclient.WantPlayMessage;
import ru.glitchless.data.models.toclient.GameInitState;
import ru.glitchless.data.models.toclient.WebSocketMessageException;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(WantPlayMessage.class),
        @JsonSubTypes.Type(GameInitState.class),
        @JsonSubTypes.Type(WebSocketMessageException.class),
})
public abstract class WebSocketMessage {
}
