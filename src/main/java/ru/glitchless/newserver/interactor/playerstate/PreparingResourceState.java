package ru.glitchless.newserver.interactor.playerstate;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.glitchless.game.data.packages.fromclient.WantPlayMessage;
import ru.glitchless.game.data.packages.toclient.GameInitState;
import ru.glitchless.newserver.data.model.ClientState;
import ru.glitchless.newserver.data.model.WebSocketMessage;
import ru.glitchless.newserver.data.model.WebSocketUser;
import ru.glitchless.newserver.repository.lobby.PlayerRepository;

public class PreparingResourceState implements IPlayerState {
    private final WebSocketUser secondUser;
    private final PlayerRepository playerRepository;

    public PreparingResourceState(PlayerRepository playerRepository, WebSocketUser secondUser) {
        this.secondUser = secondUser;
        this.playerRepository = playerRepository;
    }

    @Override
    public void processPacket(@NotNull WebSocketMessage message, @Nullable WebSocketUser forUser) {
        if (forUser == null || !(message instanceof WantPlayMessage)) {
            return;
        }

        final WantPlayMessage wantPlayMessage = (WantPlayMessage) message;

        if (wantPlayMessage.getState() != ClientState.WAITING_ENEMY_RESOURCE.getId()) {
            return;
        }

        playerRepository.putPlayerState(forUser, new WaitingEnemyState(playerRepository, secondUser));

        final IPlayerState enemyState = playerRepository.getPlayerState(secondUser);

        if (enemyState instanceof WaitingEnemyState) {
            //TODO ЗАПУСКАЕМ ИГРУ
        }
    }

    @Override
    public GameInitState getMessageForState() {
        final GameInitState gameInitState = new GameInitState();
        gameInitState.setState(ClientState.PREPARING_RESOURCE.getId());
        return gameInitState;
    }
}
