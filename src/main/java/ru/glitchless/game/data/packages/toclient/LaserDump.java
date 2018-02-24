package ru.glitchless.game.data.packages.toclient;

import ru.glitchless.game.data.Point;
import ru.glitchless.game.data.Vector;
import ru.glitchless.game.data.physics.Laser;
import ru.glitchless.newserver.data.model.WebSocketMessage;

public class LaserDump extends WebSocketMessage {
    private String type;
    private int objectId;
    private Point coord;
    private Vector speed;

    public LaserDump(Laser laser) {
        this.type = "LaserDump";
        this.coord = laser.getPoint();
        this.objectId = laser.getObjectId();
        this.speed = laser.getSpeed();
    }

    public String getType() {
        return type;
    }

    public int getObjectId() {
        return objectId;
    }

    public Point getCoord() {
        return coord;
    }

    public Vector getSpeed() {
        return speed;
    }
}
