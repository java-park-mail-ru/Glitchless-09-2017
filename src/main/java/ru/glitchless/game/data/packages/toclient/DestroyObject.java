package ru.glitchless.game.data.packages.toclient;

import ru.glitchless.game.data.physics.base.PhysicObject;
import ru.glitchless.newserver.data.model.WebSocketMessage;

public class DestroyObject extends WebSocketMessage {
    private int id;
    private String type;

    public DestroyObject(PhysicObject physicObject) {
        this.type = "DestroyObject";
        this.id = physicObject.getObjectId();
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }
}
