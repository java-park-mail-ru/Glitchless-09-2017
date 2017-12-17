package ru.glitchless.newserver.repository.game;

import org.springframework.stereotype.Component;
import ru.glitchless.newserver.data.model.RoomUsers;
import ru.glitchless.newserver.data.model.WebSocketUser;
import ru.glitchless.newserver.data.stores.GameStore;
import ru.glitchless.newserver.repository.lobby.PlayerRepository;
import ru.glitchless.newserver.utils.Constants;
import ru.glitchless.newserver.utils.SendMessageService;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
public class GameRepository {
    private final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(
            Runtime.getRuntime().availableProcessors() * 2);
    private final SendMessageService sendMessageService;
    private final PlayerRepository playerRepository;

    public GameRepository(SendMessageService sendMessageService, PlayerRepository playerRepository) {
        this.sendMessageService = sendMessageService;
        this.playerRepository = playerRepository;
    }

    public GameStore initNewGame(WebSocketUser firstUser, WebSocketUser secondUser) {
        final GameStore gameStore = new GameStore(
                new RoomUsers(firstUser, secondUser),
                sendMessageService,
                playerRepository
        );

        executor.scheduleAtFixedRate(gameStore, 0L, Constants.STEP_TIME, TimeUnit.MILLISECONDS);

        return gameStore;
    }
}
