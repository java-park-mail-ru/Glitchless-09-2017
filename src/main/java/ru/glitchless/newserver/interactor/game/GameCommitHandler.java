package ru.glitchless.newserver.interactor.game;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import ru.glitchless.game.data.packages.fromclient.ClientCommitMessage;
import ru.glitchless.newserver.data.model.WebSocketUser;
import ru.glitchless.newserver.view.websocket.SocketMessageHandler;
import ru.glitchless.newserver.data.throwables.HandleException;

@Component
public class GameCommitHandler extends SocketMessageHandler<ClientCommitMessage> {

    public GameCommitHandler(/*RoomService roomService*/) {
        super(ClientCommitMessage.class);
    }

    @Override
    public void handle(@NotNull ClientCommitMessage message, @NotNull WebSocketUser forUser) throws HandleException {

    }
}
