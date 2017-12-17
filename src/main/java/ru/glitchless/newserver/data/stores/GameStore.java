package ru.glitchless.newserver.data.stores;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.glitchless.game.GameMechanic;
import ru.glitchless.newserver.data.IGameMechanic;
import ru.glitchless.newserver.data.model.RoomUsers;
import ru.glitchless.newserver.repository.lobby.PlayerRepository;
import ru.glitchless.newserver.utils.Constants;
import ru.glitchless.newserver.utils.SendMessageService;

import java.time.Clock;

public class GameStore implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(GameStore.class);

    private final Clock clock = Clock.systemDefaultZone();
    private final IGameMechanic gameMechanic;
    private long lastFrameMillis = Constants.STEP_TIME;

    private final PlayerRepository playerRepository;
    private final RoomUsers roomUsers;
    private final SendMessageService sendMessageService;

    public GameStore(RoomUsers roomUsers, SendMessageService sendMessageService, PlayerRepository playerRepository) {
        this.gameMechanic = new GameMechanic(roomUsers, sendMessageService);
        this.playerRepository = playerRepository;
        this.roomUsers = roomUsers;
        this.sendMessageService = sendMessageService;
    }

    @Override
    public void run() {
        synchronized (this) {
            syncTick();
        }
    }

    private void syncTick() {
        final boolean isDestroy = gameMechanic.isDestroy();
        try {
            final long before = clock.millis();

            gameMechanic.tick(lastFrameMillis);

            final long after = clock.millis();

            if ((after - before) > Constants.STEP_TIME) {
                LOGGER.warn("Laggs!");
            }

            try {
                final long sleepingTime = Math.max(0, Constants.STEP_TIME - (after - before));
                Thread.sleep(sleepingTime);
            } catch (InterruptedException e) {
                LOGGER.error("Mechanics thread was interrupted", e);
            }

            if (isDestroy) {
                onDestroy();
            }
            final long afterSleep = clock.millis();
            lastFrameMillis = afterSleep - before;
        } catch (RuntimeException e) {
            LOGGER.error("Mechanics executor was reseted due to exception", e);
            onDestroy();
        }
    }

    private void onDestroy() {
        gameMechanic.onDestroy();

        playerRepository.logoutUser(roomUsers.getFirstUser());
        playerRepository.logoutUser(roomUsers.getSecondUser());

        throw new RuntimeException("Tick end"); // Грязный, но действенный способ
    }

    public IGameMechanic getGameMechanic() {
        return gameMechanic;
    }
}
