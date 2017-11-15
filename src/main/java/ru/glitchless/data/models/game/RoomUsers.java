package ru.glitchless.data.models.game;

import org.jetbrains.annotations.Nullable;

public class RoomUsers {
    private WebSocketUser firstUser;
    private WebSocketUser secondUser;

    public RoomUsers(WebSocketUser firstUser, @Nullable WebSocketUser secondUser) {
        this.firstUser = firstUser;
        this.secondUser = secondUser;
    }

    public WebSocketUser getFirstUser() {
        return firstUser;
    }

    public WebSocketUser getSecondUser() {
        return secondUser;
    }
}
