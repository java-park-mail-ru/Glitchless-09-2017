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

    public WaitingEnemyState(PlayerRepository playerRepository, WebSocketUser secondUser) {
        WebSocketUser secondUser1 = secondUser;
        this.playerRepository = playerRepository;
    }

    @Override
    public void processPacket(@NotNull WebSocketMessage message, @Nullable WebSocketUser forUser) {
        if (forUser == null) {
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
