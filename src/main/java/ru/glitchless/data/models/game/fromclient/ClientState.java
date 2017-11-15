package ru.glitchless.data.models.game.fromclient;

public enum ClientState {
    INIT(1),
    WAITING_PLAYER(2),
    WAITING_ROOM(3),
    READY(10);

    private int id;

    ClientState(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
