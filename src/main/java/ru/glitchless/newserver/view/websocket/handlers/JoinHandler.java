package ru.glitchless.newserver.view.websocket.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.glitchless.game.data.packages.fromclient.ClientState;
import ru.glitchless.game.data.packages.fromclient.WantPlayMessage;
import ru.glitchless.newserver.view.websocket.SocketMessageHandler;
import ru.glitchless.server.data.models.WebSocketMessage;
import ru.glitchless.server.data.models.WebSocketUser;
import ru.glitchless.server.data.throwables.HandleException;
import ru.glitchless.server.repositories.game.LobbyService;

import java.io.IOException;

@Component
public class JoinHandler extends SocketMessageHandler<WantPlayMessage> {
    private static final Logger LOGGER = LoggerFactory.getLogger(JoinHandler.class);

    private LobbyService lobbyService;
    private ObjectMapper objectMapper;

    public JoinHandler(/* TODO LobbyService lobbyService ,*/ ObjectMapper objectMapper) {
        super(WantPlayMessage.class);
        this.lobbyService = null;
        this.objectMapper = objectMapper;
    }

    @Override
    @SuppressWarnings("OverlyBroadCatchBlock")
    public void handle(@NotNull WantPlayMessage message, @NotNull WebSocketUser forUser) throws HandleException {
        if (message.getState() == ClientState.INIT.getId()) {
            final WebSocketMessage outputMsg = lobbyService.onInitUser(forUser);
            if (outputMsg != null) {
                try {
                    forUser.sendToUser(outputMsg, objectMapper);
                } catch (IOException e) {
                    LOGGER.error("Error while send message", e);
                }
            }
        }
    }
}
