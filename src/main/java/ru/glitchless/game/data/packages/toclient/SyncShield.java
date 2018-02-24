package ru.glitchless.game.data.packages.toclient;

import ru.glitchless.game.data.physics.ForceField;
import ru.glitchless.newserver.data.model.WebSocketMessage;

public class SyncShield extends WebSocketMessage {
    private String type;
    private int objectId;
    private int shieldVal;

    public SyncShield(ForceField forceField) {
        this.type = "SyncShield";
        this.objectId = forceField.getObjectId();
        this.shieldVal = (int) forceField.getShieldStatus();
    }

    public String getType() {
        return type;
    }

    public int getObjectId() {
        return objectId;
    }

    public int getShieldVal() {
        return shieldVal;
    }
}
