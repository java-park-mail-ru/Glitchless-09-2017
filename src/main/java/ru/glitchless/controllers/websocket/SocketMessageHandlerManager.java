package ru.glitchless.controllers.websocket;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.glitchless.controllers.websocket.handlers.JoinHandler;
import ru.glitchless.data.models.WebSocketMessage;
import ru.glitchless.data.models.fromclient.WantPlayMessage;
import ru.glitchless.data.models.game.WebSocketUser;
import ru.glitchless.data.throwables.HandleException;

import java.util.HashMap;
import java.util.Map;

@Service
public class SocketMessageHandlerManager {
    @NotNull
    private static final Logger LOGGER = LoggerFactory.getLogger(SocketMessageHandlerManager.class);
    private final Map<Class<?>, SocketMessageHandler<?>> handlers = new HashMap<>();

    public SocketMessageHandlerManager(JoinHandler joinHandler) {
        registerHandler(WantPlayMessage.class, joinHandler);
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