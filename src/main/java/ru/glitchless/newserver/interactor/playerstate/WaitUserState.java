package ru.glitchless.newserver.interactor.playerstate;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Component;
import ru.glitchless.newserver.data.model.ClientState;
import ru.glitchless.game.data.packages.toclient.GameInitState;
import ru.glitchless.newserver.data.model.WebSocketMessage;
import ru.glitchless.newserver.data.model.WebSocketUser;

@Component
public class WaitUserState implements IPlayerState {
    @Override
    public void processPacket(@NotNull WebSocketMessage message, @Nullable WebSocketUser forUser) {

    }

    @Override
    public GameInitState getMessageForState() {
        final GameInitState gameInitState = new GameInitState();
        gameInitState.setState(ClientState.WAITING_USER.getId());
        return gameInitState;
    }
}
