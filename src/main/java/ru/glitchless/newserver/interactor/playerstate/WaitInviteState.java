package ru.glitchless.newserver.interactor.playerstate;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.glitchless.game.data.packages.fromclient.WantPlayMessage;
import ru.glitchless.game.data.packages.toclient.GameInitState;
import ru.glitchless.newserver.data.model.ClientState;
import ru.glitchless.newserver.data.model.WebSocketMessage;
import ru.glitchless.newserver.data.model.WebSocketUser;
import ru.glitchless.newserver.repository.lobby.InviteRepository;
import ru.glitchless.newserver.repository.lobby.PlayerRepository;

public class WaitInviteState implements IPlayerState {
    private final InviteRepository inviteRepository;
    private final String refLink;
    private final PlayerRepository playerRepository;
    private final WaitUserState waitUserState;

    public WaitInviteState(InviteRepository inviteRepository, PlayerRepository playerRepository,
                           WebSocketUser webSocketUser, WaitUserState waitUserState) {
        this.inviteRepository = inviteRepository;
        this.refLink = inviteRepository.generateInvite(webSocketUser);
        this.playerRepository = playerRepository;
        this.waitUserState = waitUserState;
    }

    public static boolean checkToInvite(WebSocketMessage message) {
        if (!(message instanceof WantPlayMessage)) {
            return false;
        }

        if (((WantPlayMessage) message).getData() == null) {
            return false;
        }

        if (!(((WantPlayMessage) message).getData() instanceof String)) {
            return false;
        }

        if (!(((String) ((WantPlayMessage) message).getData()).startsWith("ref"))) {
            return false;
        }

        return true;
    }

    @Override
    public void processPacket(@NotNull WebSocketMessage message, @Nullable WebSocketUser forUser) {
        if (forUser == null) {
            return;
        }

        if (checkToInvite(message)) {
            inviteRepository.replaceUser(refLink, forUser);
            playerRepository.putPlayerState(forUser, this);
            return;
        }

        playerRepository.putPlayerState(forUser, waitUserState);

    }

    @Override
    public GameInitState getMessageForState() {
        final GameInitState gameInitState = new GameInitState();
        gameInitState.setState(ClientState.WAITING_USER_BY_INVITE.getId());
        gameInitState.setData("ref:" + refLink);
        return gameInitState;
    }
}
