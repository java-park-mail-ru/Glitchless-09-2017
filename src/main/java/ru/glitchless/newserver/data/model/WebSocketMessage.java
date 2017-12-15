package ru.glitchless.newserver.data.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import ru.glitchless.game.data.packages.fromclient.ClientCommitMessage;
import ru.glitchless.game.data.packages.fromclient.WantPlayMessage;
import ru.glitchless.game.data.packages.toclient.*;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(WantPlayMessage.class),
        @JsonSubTypes.Type(GameInitState.class),
        @JsonSubTypes.Type(WebSocketMessageError.class),
        @JsonSubTypes.Type(FullSwapScene.class),
        @JsonSubTypes.Type(ClientCommitMessage.class),
        @JsonSubTypes.Type(ServerSnapMessage.class),
        @JsonSubTypes.Type(LightServerSnapMessage.class),
})
public abstract class WebSocketMessage {
}
