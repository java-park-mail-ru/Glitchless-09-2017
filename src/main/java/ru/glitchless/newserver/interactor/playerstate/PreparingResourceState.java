package ru.glitchless.newserver.interactor.playerstate;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.glitchless.game.GameMechanic;
import ru.glitchless.game.data.packages.fromclient.WantPlayMessage;
import ru.glitchless.game.data.packages.toclient.FullSwapScene;
import ru.glitchless.game.data.packages.toclient.GameInitState;
import ru.glitchless.newserver.data.model.ClientState;
import ru.glitchless.newserver.data.model.WebSocketMessage;
import ru.glitchless.newserver.data.model.WebSocketUser;
import ru.glitchless.newserver.data.stores.GameStore;
import ru.glitchless.newserver.repository.game.GameRepository;
import ru.glitchless.newserver.repository.lobby.PlayerRepository;
import ru.glitchless.newserver.utils.SendMessageService;

public class PreparingResourceState implements IPlayerState {
    private static final Logger LOGGER = LoggerFactory.getLogger(PreparingResourceState.class);

    private final WebSocketUser secondUser;
    private final PlayerRepository playerRepository;
    private final GameRepository gameRepository;
    private final SendMessageService sendMessageService;

    public PreparingResourceState(PlayerRepository playerRepository,
                                  WebSocketUser secondUser,
                                  GameRepository gameRepository,
                                  SendMessageService sendMessageService) {
        this.secondUser = secondUser;
        this.playerRepository = playerRepository;
        this.gameRepository = gameRepository;
        this.sendMessageService = sendMessageService;
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
            final GameStore gameStore = this.gameRepository.initNewGame(forUser, secondUser);
            final IPlayerState state = new PlayingState(gameStore, sendMessageService);

            playerRepository.putPlayerState(forUser, state);
            playerRepository.putPlayerState(secondUser, state);

            if (gameStore.getGameMechanic() instanceof GameMechanic) {
                final FullSwapScene fullSwapScene = gameStore.getGameMechanic().dumpSwapScene();

                sendMessageService.sendMessage(fullSwapScene, forUser);
                sendMessageService.sendMessage(fullSwapScene, secondUser);
            }
        }
    }

    @Override
    public GameInitState getMessageForState() {
        final GameInitState gameInitState = new GameInitState();
        gameInitState.setState(ClientState.PREPARING_RESOURCE.getId());
        return gameInitState;
    }
}
