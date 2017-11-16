package ru.glitchless.server.controllers.websocket.handlers;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import ru.glitchless.game.data.packages.fromclient.ClientCommitMessage;
import ru.glitchless.server.controllers.websocket.SocketMessageHandler;
import ru.glitchless.server.data.models.WebSocketUser;
import ru.glitchless.server.data.models.game.GameRoom;
import ru.glitchless.server.data.throwables.HandleException;
import ru.glitchless.server.repositories.game.RoomService;

@Component
public class GameCommitHandler extends SocketMessageHandler<ClientCommitMessage> {
    private RoomService roomService;

    public GameCommitHandler(RoomService roomService) {
        super(ClientCommitMessage.class);
        this.roomService = roomService;
    }

    @Override
    public void handle(@NotNull ClientCommitMessage message, @NotNull WebSocketUser forUser) throws HandleException {
        final GameRoom room = roomService.getRoomByUser(forUser);
        if (room == null) {
            throw new HandleException("Room not found");
        }

        if (room.getMechanic() == null) {
            throw new HandleException("Internal error. Not found mechanic implementation");
        }

        room.getMechanic().onNewPacket(message, forUser);
    }
}
