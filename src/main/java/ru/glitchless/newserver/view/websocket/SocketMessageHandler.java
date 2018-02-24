package ru.glitchless.newserver.view.websocket;

import org.jetbrains.annotations.NotNull;
import ru.glitchless.newserver.data.model.WebSocketMessage;
import ru.glitchless.newserver.data.model.WebSocketUser;
import ru.glitchless.newserver.data.throwables.HandleException;

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
            throw ex;
        }
    }

    public abstract void handle(@NotNull T message, @NotNull WebSocketUser forUser) throws HandleException;
}
