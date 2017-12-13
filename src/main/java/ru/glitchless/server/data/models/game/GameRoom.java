package ru.glitchless.server.data.models.game;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.glitchless.server.data.models.IGameMechanic;
import ru.glitchless.newserver.utils.Constants;

import java.time.Clock;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class GameRoom implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(GameRoom.class);
    private static final long STEP_TIME = TimeUnit.SECONDS.toMillis(1) / Constants.TPS;

    private final int roomId;
    private List<IDestroyListener> destroyListeners = Collections.synchronizedList(new ArrayList<>());
    private Clock clock = Clock.systemDefaultZone();
    private Executor tickExecutor = Executors.newSingleThreadExecutor();
    private IGameMechanic mechanic;

    public GameRoom(int roomId) {
        this.roomId = roomId;
    }

    public void init(@NotNull IGameMechanic gameMechanic) {
        this.mechanic = gameMechanic;
        tickExecutor.execute(this);
    }

    public IGameMechanic getMechanic() {
        return mechanic;
    }

    public void addDestroyListener(IDestroyListener listener) {
        destroyListeners.add(listener);
    }

    public void destroy() {
        destroyListeners.forEach(IDestroyListener::onDestroy);
    }

    @Override
    public void run() {
        try {
            mainTick();
        } finally {
            LOGGER.error("Mechanic executor terminated");
            destroy();
        }
    }

    private void mainTick() {
        long lastFrameMillis = STEP_TIME;
        while (true) {
            try {
                final long before = clock.millis();

                mechanic.tick(lastFrameMillis);

                final long after = clock.millis();

                if ((after - before) > STEP_TIME) {
                    LOGGER.warn("Laggs!");
                }

                try {
                    final long sleepingTime = Math.max(0, STEP_TIME - (after - before));
                    Thread.sleep(sleepingTime);
                } catch (InterruptedException e) {
                    LOGGER.error("Mechanics thread was interrupted", e);
                }

                if (Thread.currentThread().isInterrupted()) {
                    mechanic.onDestroy();
                    return;
                }
                final long afterSleep = clock.millis();
                lastFrameMillis = afterSleep - before;
            } catch (RuntimeException e) {
                LOGGER.error("Mechanics executor was reseted due to exception", e);
                mechanic.onDestroy();
            }
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof GameRoom
                && ((GameRoom) obj).roomId == roomId) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return roomId;
    }
}
