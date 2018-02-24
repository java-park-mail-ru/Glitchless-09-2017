package ru.glitchless.game.data.packages.toclient;

import ru.glitchless.newserver.data.model.WebSocketMessage;

public class SingleSnapObject extends WebSocketMessage {
    private SnapObject singleSnapObject;
    private String type;

    public SingleSnapObject(SnapObject singleSnapObject) {
        this.singleSnapObject = singleSnapObject;
        this.type = "SingleSnapObject";
    }

    public String getType() {
        return type;
    }

    public SnapObject getSingleSnapObject() {
        return singleSnapObject;
    }
}
