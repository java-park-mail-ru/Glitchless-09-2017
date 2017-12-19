package ru.glitchless.newserver.interactor.playerstate;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.glitchless.game.data.packages.toclient.GameInitState;
import ru.glitchless.newserver.data.model.ClientState;
import ru.glitchless.newserver.data.model.WebSocketMessage;
import ru.glitchless.newserver.data.model.WebSocketUser;
import ru.glitchless.newserver.repository.lobby.InviteRepository;

public class WaitInviteState implements IPlayerState {
    private final InviteRepository inviteRepository;
    private final String refLink;

    public WaitInviteState(InviteRepository inviteRepository, WebSocketUser webSocketUser) {
        this.inviteRepository = inviteRepository;
        refLink = inviteRepository.generateInvite(webSocketUser);
    }


    @Override
    public void processPacket(@NotNull WebSocketMessage message, @Nullable WebSocketUser forUser) {
        if (forUser == null) {
            return;
        }
        inviteRepository.replaceUser(refLink, forUser);
    }

    @Override
    public GameInitState getMessageForState() {
        final GameInitState gameInitState = new GameInitState();
        gameInitState.setState(ClientState.WAITING_USER.getId());
        gameInitState.setData("ref:" + refLink);
        return gameInitState;
    }
}
