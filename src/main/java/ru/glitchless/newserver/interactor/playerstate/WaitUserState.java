package ru.glitchless.newserver.interactor.playerstate;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Component;
import ru.glitchless.newserver.data.model.ClientState;
import ru.glitchless.game.data.packages.toclient.GameInitState;
import ru.glitchless.newserver.data.model.WebSocketMessage;
import ru.glitchless.newserver.data.model.WebSocketUser;
import ru.glitchless.newserver.repository.lobby.PlayerRepository;

@Component
public class WaitUserState implements IPlayerState {
    private final PlayerRepository playerRepository;

    public WaitUserState(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public void processPacket(@NotNull WebSocketMessage message, @Nullable WebSocketUser forUser) {
        if (forUser == null) {
            return;
        }

        final WebSocketUser secondUser = this.playerRepository.getPlayerWithState(forUser, this);

        if (secondUser == null) {
            return;
        }

        playerRepository.putPlayerState(forUser, new PreparingResourceState(secondUser));
        playerRepository.putPlayerState(secondUser, new PreparingResourceState(forUser));
    }

    @Override
    public GameInitState getMessageForState() {
        final GameInitState gameInitState = new GameInitState();
        gameInitState.setState(ClientState.WAITING_USER.getId());
        return gameInitState;
    }
}
