package ru.glitchless.server.data.models;

public interface IGameMechanic {

    void tick(long elapsedMS);

    void onNewPacket(WebSocketMessage message, WebSocketUser webSocketUser);

    void onDestroy();
}
