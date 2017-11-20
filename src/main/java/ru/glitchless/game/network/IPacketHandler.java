package ru.glitchless.game.network;

import ru.glitchless.game.data.exceptions.GameException;
import ru.glitchless.game.data.packages.fromclient.ClientCommitMessage;
import ru.glitchless.game.data.packages.toclient.LightServerSnapMessage;
import ru.glitchless.game.data.packages.toclient.ServerSnapMessage;
import ru.glitchless.game.data.physics.base.PhysicObject;
import ru.glitchless.server.utils.Pair;

public abstract class IPacketHandler<T extends PhysicObject> {
    private Class<T> clazz;

    public IPacketHandler(Class<T> clazz) {
        this.clazz = clazz;
    }

    public Pair<LightServerSnapMessage, ServerSnapMessage> processPacket(PhysicObject gameObject, ClientCommitMessage clientCommitMessage) {
        try {
            return handle(clazz.cast(gameObject), clientCommitMessage);
        } catch (ClassCastException e) {
            throw new GameException("Cant cast " + gameObject.getClass() + " to " + clazz, e);
        }
    }

    public abstract Pair<LightServerSnapMessage, ServerSnapMessage> handle(T gameObject, ClientCommitMessage clientCommitMessage);
}
