package ru.glitchless.controllers.websocket;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.glitchless.data.models.UserModel;
import ru.glitchless.data.models.WebSocketMessage;
import ru.glitchless.data.throwables.HandleException;

import java.util.HashMap;
import java.util.Map;

@Service
public class SocketMessageHandlerManager {
    @NotNull
    private static final Logger LOGGER = LoggerFactory.getLogger(SocketMessageHandlerManager.class);
    private final Map<Class<?>, SocketMessageHandler<?>> handlers = new HashMap<>();


    public void handle(@NotNull WebSocketMessage message, @NotNull UserModel forUser) throws HandleException {

        final SocketMessageHandler<?> messageHandler = handlers.get(message.getClass());
        if (messageHandler == null) {
            throw new HandleException("No handler for message of " + message.getClass().getName() + " type");
        }
        messageHandler.handleMessage(message, forUser);
        LOGGER.trace("Message handled: type =[" + message.getClass().getName() + ']');
    }

}
