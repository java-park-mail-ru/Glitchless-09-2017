package ru.glitchless.newserver.repository.game;

import org.springframework.stereotype.Component;
import ru.glitchless.game.GameMechanic;
import ru.glitchless.newserver.data.model.RoomUsers;
import ru.glitchless.newserver.data.model.WebSocketUser;
import ru.glitchless.newserver.data.stores.GameStore;
import ru.glitchless.newserver.utils.Constants;
import ru.glitchless.newserver.utils.SendMessageService;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
public class GameRepository {
    private final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
    private final SendMessageService sendMessageService;

    public GameRepository(SendMessageService sendMessageService) {
        this.sendMessageService = sendMessageService;
    }

    public GameStore initNewGame(WebSocketUser firstUser, WebSocketUser secondUser) {
        final GameStore gameStore = new GameStore(
                new GameMechanic(
                        new RoomUsers(firstUser, secondUser),
                        sendMessageService)
        );

        executor.scheduleAtFixedRate(gameStore, 0L, Constants.STEP_TIME, TimeUnit.MILLISECONDS);

        return gameStore;
    }
}
