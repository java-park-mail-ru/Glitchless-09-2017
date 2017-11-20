package ru.glitchless.game.network.handlers;

import ru.glitchless.game.data.Vector;
import ru.glitchless.game.data.packages.fromclient.ClientCommitMessage;
import ru.glitchless.game.data.packages.toclient.LightServerSnapMessage;
import ru.glitchless.game.data.packages.toclient.ServerSnapMessage;
import ru.glitchless.game.data.physics.Platform;
import ru.glitchless.game.network.IPacketHandler;
import ru.glitchless.server.utils.Pair;

public class PlatformHandler extends IPacketHandler<Platform> {
    public PlatformHandler() {
        super(Platform.class);
    }

    @Override
    public Pair<LightServerSnapMessage, ServerSnapMessage> handle(Platform gameObject, ClientCommitMessage clientCommitMessage) {
        final Vector vector = clientCommitMessage.getVector(); // X - rotation speed
        gameObject.setRotationSpeed(vector.getDiffX());

        final LightServerSnapMessage lightServerSnapMessage = new LightServerSnapMessage(gameObject, clientCommitMessage);
        final ServerSnapMessage serverSnapMessage = new ServerSnapMessage(gameObject);

        return new Pair<>(lightServerSnapMessage, serverSnapMessage);
    }
}
