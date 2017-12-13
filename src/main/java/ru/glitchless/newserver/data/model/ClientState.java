package ru.glitchless.newserver.data.model;

public enum ClientState {
    WAITING_USER(2),
    PREPARING_RESOURCE(3),
    WAITING_ENEMY_RESOURCE(4),
    READY(10);

    private int id;

    ClientState(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
