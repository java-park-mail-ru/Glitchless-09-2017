package ru.glitchless.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.glitchless.game.data.packages.toclient.FullSwapScene;
import ru.glitchless.server.data.models.IGameMechanic;
import ru.glitchless.server.data.models.WebSocketMessage;
import ru.glitchless.server.data.models.WebSocketUser;
import ru.glitchless.server.data.models.game.RoomUsers;

public class GameMechanic implements IGameMechanic {
    private static final Logger LOGGER = LoggerFactory.getLogger(GameMechanic.class);

    private RoomUsers roomUsers;

    public GameMechanic(RoomUsers roomUsers) {
        this.roomUsers = roomUsers;
    }

    @Override
    public void tick(long elapsedMS) {

    }

    @Override
    public void onNewPacket(WebSocketMessage message, WebSocketUser webSocketUser) {

    }

    @Override
    public void onDestroy() {

    }

    public FullSwapScene firstSetting() {
        return new FullSwapScene(); // Init all game element
    }
}
