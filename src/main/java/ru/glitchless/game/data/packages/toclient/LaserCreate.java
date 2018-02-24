package ru.glitchless.game.data.packages.toclient;

import ru.glitchless.game.data.Vector;
import ru.glitchless.game.data.physics.Laser;
import ru.glitchless.newserver.data.model.WebSocketMessage;

public class LaserCreate extends WebSocketMessage {
    private String type;
    private int objectId;
    private Vector speed;

    public LaserCreate(Laser laser) {
        this.type = "LaserCreate";
        this.speed = laser.getSpeed();
        this.objectId = laser.getObjectId();
    }

    public String getType() {
        return type;
    }

    public int getObjectId() {
        return objectId;
    }

    public Vector getSpeed() {
        return speed;
    }
}
