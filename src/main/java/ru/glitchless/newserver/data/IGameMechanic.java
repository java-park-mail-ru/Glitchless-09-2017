package ru.glitchless.newserver.data;

import ru.glitchless.game.data.packages.fromclient.ClientCommitMessage;
import ru.glitchless.game.data.packages.toclient.FullSwapScene;
import ru.glitchless.newserver.data.model.WebSocketUser;

public interface IGameMechanic {

    boolean isDestroy();

    void tick(long elapsedMS);

    void onNewPacket(ClientCommitMessage message, WebSocketUser webSocketUser);

    void onDestroy();

    FullSwapScene dumpSwapScene();
}
