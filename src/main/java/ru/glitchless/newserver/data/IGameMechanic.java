package ru.glitchless.newserver.data;

import ru.glitchless.game.data.packages.fromclient.ClientCommitMessage;
import ru.glitchless.newserver.data.model.WebSocketUser;

public interface IGameMechanic {

    default boolean isDestroy() {
        return false;
    }

    void tick(long elapsedMS);

    void onNewPacket(ClientCommitMessage message, WebSocketUser webSocketUser);

    void onDestroy();
}
