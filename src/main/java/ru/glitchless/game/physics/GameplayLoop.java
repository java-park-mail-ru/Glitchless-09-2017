package ru.glitchless.game.physics;

import ru.glitchless.game.GameMechanic;
import ru.glitchless.game.data.Point;
import ru.glitchless.game.data.Vector;
import ru.glitchless.game.data.packages.toclient.CreateObjectMessage;
import ru.glitchless.game.data.physics.Laser;
import ru.glitchless.newserver.data.model.RoomUsers;
import ru.glitchless.newserver.utils.Constants;
import ru.glitchless.newserver.utils.SendMessageService;

public class GameplayLoop {
    private final RoomUsers roomUsers;
    private final SendMessageService sendMessageService;
    private final GameMechanic gameMechanic;
    private final Vector[] anglePoints = new Vector[]{
            new Vector(0.1f, -0.05f),
            new Vector(0f, -0.1f),
            new Vector(0.05f, -0.1f),
            new Vector(0.1f, -0.1f),
            new Vector(0.1f, -0.05f),
            new Vector(0.1f, 0f),
            new Vector(0.1f, 0.05f),
            new Vector(0.1f, 0.1f)};
    private int angleCounter = 0;
    private long counter;

    public GameplayLoop(RoomUsers roomUsers, SendMessageService sendMessageService, GameMechanic gameMechanic) {
        this.roomUsers = roomUsers;
        this.sendMessageService = sendMessageService;
        this.gameMechanic = gameMechanic;
    }

    public void processGameplay(long elapsedMs) {
        counter += elapsedMs;
        if (counter < Constants.LASER_PER_TIME) {
            return;
        }
        counter = 0;

        final Laser laser = new Laser(new Point(Constants.GAME_FIELD_SIZE.getPosX() / 2,
                Constants.GAME_FIELD_SIZE.getPosY() / 2));

        final Vector laserSpeed = this.anglePoints[Math.abs(this.angleCounter % this.anglePoints.length)].multipy(-1).clone().multipy(-2);
        laser.setSpeed(laserSpeed);
        angleCounter++;

        gameMechanic.putObject(laser);

        sendMessageService.sendMessage(new CreateObjectMessage(laser), roomUsers);
    }


}
