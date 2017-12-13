package ru.glitchless.newserver.view.websocket;

import org.jetbrains.annotations.NotNull;
import ru.glitchless.server.data.models.WebSocketMessage;
import ru.glitchless.server.data.models.WebSocketUser;
import ru.glitchless.server.data.throwables.HandleException;

public abstract class SocketMessageHandler<T extends WebSocketMessage> {
    @NotNull
    private final Class<T> clazz;

    public SocketMessageHandler(@NotNull Class<T> clazz) {
        this.clazz = clazz;
    }

    public void handleMessage(@NotNull WebSocketMessage message, @NotNull WebSocketUser forUser) throws HandleException {
        try {
            handle(clazz.cast(message), forUser);
        } catch (ClassCastException ex) {
            throw new HandleException("Can't read incoming message of type " + message.getClass(), ex);
        }
    }

    public abstract void handle(@NotNull T message, @NotNull WebSocketUser forUser) throws HandleException;
}
