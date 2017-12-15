package ru.glitchless.game.network;

import ru.glitchless.game.data.ProcessingCommit;
import ru.glitchless.game.data.exceptions.GameException;
import ru.glitchless.game.data.packages.fromclient.ClientCommitMessage;
import ru.glitchless.game.data.packages.toclient.LightServerSnapMessage;
import ru.glitchless.game.data.packages.toclient.ServerSnapMessage;
import ru.glitchless.game.data.physics.Platform;
import ru.glitchless.game.data.physics.base.PhysicObject;
import ru.glitchless.game.network.handlers.PlatformHandler;
import ru.glitchless.newserver.data.model.WebSocketUser;
import ru.glitchless.newserver.utils.Pair;

import java.util.HashMap;

public class PacketHandlerManager {
    private final HashMap<Integer, PhysicObject> idToObject;
    private final HashMap<Class, IPacketHandler> packetHandlers = new HashMap<>();

    public PacketHandlerManager(HashMap<Integer, PhysicObject> idToObject) {
        this.idToObject = idToObject;
        packetHandlers.put(Platform.class, new PlatformHandler());
    }

    public Pair<LightServerSnapMessage, ServerSnapMessage> processPacket(ProcessingCommit<ClientCommitMessage> commit, WebSocketUser user) {
        final PhysicObject object = idToObject.get(commit.getMessage().getObjectId());
        if (object == null) {
            throw new GameException("Not found object with id: " + commit.getMessage().getObjectId());
        }

        final IPacketHandler<?> handler = packetHandlers.get(object.getClass());
        if (handler == null) {
            throw new GameException("Not found handlers for " + object.getClass());
        }

        return handler.processPacket(object, commit.getMessage(), user);
    }
}
