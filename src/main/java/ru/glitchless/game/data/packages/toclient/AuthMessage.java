package ru.glitchless.game.data.packages.toclient;

import ru.glitchless.newserver.data.model.WebSocketMessage;

public class AuthMessage extends WebSocketMessage {
    private String type;
    private String login;

    public AuthMessage() {
        this.type = "AuthMessage";
    }

    public AuthMessage(String login) {
        this.type = "AuthMessage";
        this.login = login;
    }

    public String getType() {
        return type;
    }

    public String getLogin() {
        return login;
    }
}
