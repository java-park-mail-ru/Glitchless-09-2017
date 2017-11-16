package ru.glitchless.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.glitchless.game.data.ProcessingCommit;
import ru.glitchless.game.data.packages.fromclient.ClientCommitMessage;
import ru.glitchless.game.data.packages.toclient.FullSwapScene;
import ru.glitchless.server.data.models.IGameMechanic;
import ru.glitchless.server.data.models.WebSocketUser;
import ru.glitchless.server.data.models.game.RoomUsers;
import ru.glitchless.server.repositories.game.SendMessageService;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class GameMechanic implements IGameMechanic {
    private static final Logger LOGGER = LoggerFactory.getLogger(GameMechanic.class);

    private Queue<ProcessingCommit<ClientCommitMessage>> waitProcessCommit
            = new ConcurrentLinkedQueue<>();
    private SendMessageService sendMessageService;
    private RoomUsers roomUsers;

    public GameMechanic(RoomUsers roomUsers, SendMessageService sendMessageService) {
        this.roomUsers = roomUsers;
        this.sendMessageService = sendMessageService;
    }

    @Override
    public void tick(long elapsedMS) {

    }

    @Override
    public void onNewPacket(ClientCommitMessage message, WebSocketUser webSocketUser) {

    }

    @Override
    public void onDestroy() {

    }

    public FullSwapScene firstSetting() {
        return new FullSwapScene(); // Init all game element
    }
}
