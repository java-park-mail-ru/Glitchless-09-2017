package ru.glitchless.game.network;

import ru.glitchless.game.data.packages.toclient.DestroyObject;
import ru.glitchless.game.data.packages.toclient.SingleSnapObject;
import ru.glitchless.game.data.packages.toclient.SnapObject;
import ru.glitchless.game.data.physics.base.PhysicObject;
import ru.glitchless.newserver.data.model.RoomUsers;
import ru.glitchless.newserver.data.model.WebSocketUser;
import ru.glitchless.newserver.utils.SendMessageService;

public class CollisionHandler {
    private final SendMessageService sendMessageService;
    private final RoomUsers roomUsers;

    public CollisionHandler(SendMessageService sendMessageService, RoomUsers roomUsers) {
        this.sendMessageService = sendMessageService;
        this.roomUsers = roomUsers;
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

    public void onHpLoss(WebSocketUser webSocketUser) {
        // TODO
    }
}
