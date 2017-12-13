package ru.glitchless.newserver.view.websocket;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.glitchless.game.data.packages.fromclient.ClientCommitMessage;
import ru.glitchless.game.data.packages.fromclient.WantPlayMessage;
import ru.glitchless.newserver.data.model.WebSocketMessage;
import ru.glitchless.newserver.data.model.WebSocketUser;
import ru.glitchless.newserver.data.throwables.HandleException;
import ru.glitchless.newserver.interactor.game.GameCommitHandler;
import ru.glitchless.newserver.interactor.lobby.JoinHandler;

import java.time.Clock;
import java.util.HashMap;
import java.util.Map;

@Service
public class SocketMessageHandlerManager {
    @NotNull
    private static final Logger LOGGER = LoggerFactory.getLogger(SocketMessageHandlerManager.class);
    private final Map<Class<?>, SocketMessageHandler<?>> handlers = new HashMap<>();
    private Clock clock = Clock.systemDefaultZone();

    public SocketMessageHandlerManager(JoinHandler joinHandler,
                                       GameCommitHandler gameCommitHandler) {
        registerHandler(WantPlayMessage.class, joinHandler);
        registerHandler(ClientCommitMessage.class, gameCommitHandler);
    }

    public void handle(@NotNull WebSocketMessage message, @NotNull WebSocketUser forUser) throws HandleException {
        final SocketMessageHandler<?> messageHandler = handlers.get(message.getClass());
        if (messageHandler == null) {
            throw new HandleException("No handlers for message of " + message.getClass().getName() + " type");
        }
        messageHandler.handleMessage(message, forUser);
        LOGGER.trace("Message handled: type =[" + message.getClass().getName() + ']');
    }

    <T extends WebSocketMessage> void registerHandler(@NotNull Class<T> clazz, SocketMessageHandler<T> handler) {
        handlers.put(clazz, handler);
    }

}
