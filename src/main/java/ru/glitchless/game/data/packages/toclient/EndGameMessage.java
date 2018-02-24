package ru.glitchless.game.data.packages.toclient;

import ru.glitchless.newserver.data.model.WebSocketMessage;
import ru.glitchless.newserver.data.model.WebSocketUser;

public class EndGameMessage extends WebSocketMessage {
    private String type;
    private String winnerLogin;

    public EndGameMessage(WebSocketUser winner) {
        this.type = "EndGameMessage";
        this.winnerLogin = winner.getUserModel().getLogin();
    }

    public String getType() {
        return type;
    }

    public String getWinnerLogin() {
        return winnerLogin;
    }
}
