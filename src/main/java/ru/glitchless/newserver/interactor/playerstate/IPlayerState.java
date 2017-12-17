package ru.glitchless.newserver.interactor.playerstate;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.glitchless.game.data.packages.toclient.GameInitState;
import ru.glitchless.newserver.data.model.WebSocketMessage;
import ru.glitchless.newserver.data.model.WebSocketUser;

public interface IPlayerState {
    void processPacket(@NotNull WebSocketMessage message, @Nullable WebSocketUser forUser);

    GameInitState getMessageForState();
}
