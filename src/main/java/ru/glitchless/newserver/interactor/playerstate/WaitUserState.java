package ru.glitchless.newserver.interactor.playerstate;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Component;
import ru.glitchless.game.data.packages.toclient.GameInitState;
import ru.glitchless.newserver.data.model.ClientState;
import ru.glitchless.newserver.data.model.WebSocketMessage;
import ru.glitchless.newserver.data.model.WebSocketUser;
import ru.glitchless.newserver.repository.game.GameRepository;
import ru.glitchless.newserver.repository.lobby.PlayerRepository;
import ru.glitchless.newserver.utils.SendMessageService;

@Component
public class WaitUserState implements IPlayerState {
    private final PlayerRepository playerRepository;
    private final GameRepository gameRepository;
    private final SendMessageService sendMessageService;

    public WaitUserState(PlayerRepository playerRepository,
                         GameRepository gameRepository,
                         SendMessageService sendMessageService) {
        this.playerRepository = playerRepository;
        this.gameRepository = gameRepository;
        this.sendMessageService = sendMessageService;
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

        playerRepository.putPlayerState(forUser,
                new PreparingResourceState(playerRepository, secondUser, gameRepository, sendMessageService));
        playerRepository.putPlayerState(secondUser,
                new PreparingResourceState(playerRepository, forUser, gameRepository, sendMessageService));
    }

    @Override
    public GameInitState getMessageForState() {
        final GameInitState gameInitState = new GameInitState();
        gameInitState.setState(ClientState.WAITING_USER.getId());
        return gameInitState;
    }
}
