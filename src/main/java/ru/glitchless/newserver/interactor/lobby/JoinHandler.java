package ru.glitchless.newserver.interactor.lobby;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.glitchless.game.data.packages.fromclient.WantPlayMessage;
import ru.glitchless.newserver.data.model.WebSocketUser;
import ru.glitchless.newserver.data.throwables.HandleException;
import ru.glitchless.newserver.interactor.playerstate.IPlayerState;
import ru.glitchless.newserver.interactor.playerstate.WaitUserState;
import ru.glitchless.newserver.repository.lobby.PlayerRepository;
import ru.glitchless.newserver.utils.SendMessageService;
import ru.glitchless.newserver.view.websocket.SocketMessageHandler;

@Component
public class JoinHandler extends SocketMessageHandler<WantPlayMessage> {
    private static final Logger LOGGER = LoggerFactory.getLogger(JoinHandler.class);

    private final PlayerRepository playerRepository;
    private final SendMessageService sendMessageService;
    private final WaitUserState templateWaitState;

    public JoinHandler(PlayerRepository playerRepository, SendMessageService sendMessageService, WaitUserState waitUserState) {
        super(WantPlayMessage.class);
        this.playerRepository = playerRepository;
        this.sendMessageService = sendMessageService;
        this.templateWaitState = waitUserState;
    }

    @Override
    @SuppressWarnings("OverlyBroadCatchBlock")
    public void handle(@NotNull WantPlayMessage message, @NotNull WebSocketUser forUser) throws HandleException {
        IPlayerState state = playerRepository.getPlayerState(forUser);

        if (state == null) {
            state = templateWaitState;
            playerRepository.putPlayerState(forUser, state);
        }

        state.processPacket(message, forUser);
    }
}
