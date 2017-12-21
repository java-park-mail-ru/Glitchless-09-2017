package ru.glitchless.newserver.interactor.playerstate;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Component;
import ru.glitchless.game.data.packages.fromclient.WantPlayMessage;
import ru.glitchless.game.data.packages.toclient.GameInitState;
import ru.glitchless.newserver.data.model.ClientState;
import ru.glitchless.newserver.data.model.WebSocketMessage;
import ru.glitchless.newserver.data.model.WebSocketUser;
import ru.glitchless.newserver.repository.game.GameRepository;
import ru.glitchless.newserver.repository.lobby.InviteRepository;
import ru.glitchless.newserver.repository.lobby.PlayerRepository;
import ru.glitchless.newserver.utils.SendMessageService;

@Component
public class WaitUserState implements IPlayerState {
    private final PlayerRepository playerRepository;
    private final GameRepository gameRepository;
    private final SendMessageService sendMessageService;
    private final InviteRepository inviteRepository;

    public WaitUserState(PlayerRepository playerRepository,
                         GameRepository gameRepository,
                         SendMessageService sendMessageService,
                         InviteRepository inviteRepository) {
        this.playerRepository = playerRepository;
        this.gameRepository = gameRepository;
        this.sendMessageService = sendMessageService;
        this.inviteRepository = inviteRepository;
    }

    @Override
    public void processPacket(@NotNull WebSocketMessage message, @Nullable WebSocketUser forUser) {
        if (forUser == null) {
            return;
        }

        final WebSocketUser secondUser;

        if (WaitInviteState.checkToInvite(message)) {
            final String reflink = (String) ((WantPlayMessage) message).getData();
            final String[] refParts = reflink.split(":");
            if (refParts.length == 1) {
                playerRepository.putPlayerState(forUser, new WaitInviteState(inviteRepository,
                        playerRepository, forUser, this));
                return;
            }
            secondUser = this.inviteRepository.getUserAndRemoveByInvite(refParts[1]);
            if (!secondUser.getSession().isOpen()) {
                playerRepository.putPlayerState(forUser, new WaitInviteState(inviteRepository,
                        playerRepository, forUser, this));
                return;
            }
        } else {
            secondUser = this.playerRepository.getPlayerWithState(forUser, this);
        }


        if (secondUser == null || !secondUser.getSession().isOpen()) {
            return;
        }

        playerRepository.putPlayerState(forUser,
                new PreparingResourceState(playerRepository, secondUser, gameRepository, sendMessageService, this));
        playerRepository.putPlayerState(secondUser,
                new PreparingResourceState(playerRepository, forUser, gameRepository, sendMessageService, this));
    }


    @Override
    public GameInitState getMessageForState() {
        final GameInitState gameInitState = new GameInitState();
        gameInitState.setState(ClientState.WAITING_USER.getId());
        return gameInitState;
    }
}
