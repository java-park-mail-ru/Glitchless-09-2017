package ru.glitchless.newserver.data.stores;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.glitchless.newserver.data.IGameMechanic;
import ru.glitchless.newserver.utils.Constants;

import java.time.Clock;

public class GameStore implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(GameStore.class);

    private final Clock clock = Clock.systemDefaultZone();
    private final IGameMechanic gameMechanic;
    private long lastFrameMillis = Constants.STEP_TIME;

    public GameStore(IGameMechanic gameMechanic) {
        this.gameMechanic = gameMechanic;
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
                gameMechanic.onDestroy();
                throw new RuntimeException("Tick end"); // Грязный, но действенный способ
            }
            final long afterSleep = clock.millis();
            lastFrameMillis = afterSleep - before;
        } catch (RuntimeException e) {
            LOGGER.error("Mechanics executor was reseted due to exception", e);
            gameMechanic.onDestroy();
        }
    }

    public IGameMechanic getGameMechanic() {
        return gameMechanic;
    }
}
