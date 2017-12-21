package ru.glitchless.newserver.interactor.playerstate;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.glitchless.game.data.packages.toclient.GameInitState;
import ru.glitchless.newserver.data.model.ClientState;
import ru.glitchless.newserver.data.model.WebSocketMessage;
import ru.glitchless.newserver.data.model.WebSocketUser;
import ru.glitchless.newserver.repository.lobby.PlayerRepository;

public class WaitingEnemyState implements IPlayerState {
    private final PlayerRepository playerRepository;
    private final WebSocketUser secondUser;
    private final WaitUserState waitUserState;

    public WaitingEnemyState(PlayerRepository playerRepository, WebSocketUser secondUser, WaitUserState waitUserState) {
        this.playerRepository = playerRepository;
        this.secondUser = secondUser;
        this.waitUserState = waitUserState;
    }

    @Override
    public void processPacket(@NotNull WebSocketMessage message, @Nullable WebSocketUser forUser) {
        if (forUser == null) {
            return;
        }

        if (!secondUser.getSession().isOpen()) {
            playerRepository.putPlayerState(forUser, waitUserState);
            return;
        }

        playerRepository.putPlayerState(forUser, this);
    }

    @Override
    public GameInitState getMessageForState() {
        final GameInitState gameInitState = new GameInitState();
        gameInitState.setState(ClientState.WAITING_ENEMY_RESOURCE.getId());
        return gameInitState;
    }
}
