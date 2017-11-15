package ru.glitchless.data.models;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import ru.glitchless.data.models.game.fromclient.WantPlayMessage;
import ru.glitchless.data.models.game.toclient.FullSwapScene;
import ru.glitchless.data.models.game.toclient.GameInitState;
import ru.glitchless.data.models.game.toclient.WebSocketMessageError;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(WantPlayMessage.class),
        @JsonSubTypes.Type(GameInitState.class),
        @JsonSubTypes.Type(WebSocketMessageError.class),
        @JsonSubTypes.Type(FullSwapScene.class),
})
public abstract class WebSocketMessage {
}
