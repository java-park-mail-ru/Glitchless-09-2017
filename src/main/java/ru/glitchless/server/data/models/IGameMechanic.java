package ru.glitchless.server.data.models;

import ru.glitchless.game.data.packages.fromclient.ClientCommitMessage;

public interface IGameMechanic {

    void tick(long elapsedMS);

    void onNewPacket(ClientCommitMessage message, WebSocketUser webSocketUser);

    void onDestroy();
}
