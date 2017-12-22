package ru.glitchless.game.network;

import ru.glitchless.game.data.packages.toclient.*;
import ru.glitchless.game.data.physics.ForceField;
import ru.glitchless.game.data.physics.base.PhysicObject;
import ru.glitchless.newserver.data.model.RoomUsers;
import ru.glitchless.newserver.data.model.WebSocketUser;
import ru.glitchless.newserver.utils.Constants;
import ru.glitchless.newserver.utils.SendMessageService;

import java.util.HashMap;
import java.util.Map;

public class CollisionHandler {
    private final SendMessageService sendMessageService;
    private final RoomUsers roomUsers;

    private Map<WebSocketUser, Integer> hpCount = new HashMap<>();

    public CollisionHandler(SendMessageService sendMessageService, RoomUsers roomUsers) {
        this.sendMessageService = sendMessageService;
        this.roomUsers = roomUsers;
        hpCount.put(roomUsers.getFirstUser(), Constants.GAME_HP_COUNT);
        hpCount.put(roomUsers.getSecondUser(), Constants.GAME_HP_COUNT);
    }

    public void onObjectChange(PhysicObject physicObject) {
        sendMessageService.sendMessage(new SingleSnapObject(
                new SnapObject(physicObject)
        ), roomUsers);
    }

    public void onDestroyObject(PhysicObject physicObject) {
        if (!physicObject.isForDestroy()) {
            return;
        }

        sendMessageService.sendMessage(new DestroyObject(physicObject), roomUsers);

    }

    public void onFieldShot(ForceField forceField) {
        sendMessageService.sendMessage(new SyncShield(forceField), roomUsers);
    }

    public void onHpLoss(WebSocketUser webSocketUser) {
        int newHpCount = hpCount.get(webSocketUser) - 1;
        hpCount.put(webSocketUser, newHpCount);
        if (newHpCount <= 0) {
            sendMessageService.sendMessage(new EndGameMessage(webSocketUser), roomUsers);
        }
    }
}
