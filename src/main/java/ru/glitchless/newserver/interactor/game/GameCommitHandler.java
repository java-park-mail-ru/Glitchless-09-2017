package ru.glitchless.newserver.interactor.game;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import ru.glitchless.game.data.packages.fromclient.ClientCommitMessage;
import ru.glitchless.newserver.data.model.WebSocketUser;
import ru.glitchless.newserver.data.throwables.HandleException;
import ru.glitchless.newserver.interactor.playerstate.IPlayerState;
import ru.glitchless.newserver.interactor.playerstate.PlayingState;
import ru.glitchless.newserver.repository.lobby.PlayerRepository;
import ru.glitchless.newserver.view.websocket.SocketMessageHandler;

@Component
public class GameCommitHandler extends SocketMessageHandler<ClientCommitMessage> {
    private final PlayerRepository playerRepository;

    public GameCommitHandler(PlayerRepository playerRepository) {
        super(ClientCommitMessage.class);
        this.playerRepository = playerRepository;
    }

    @Override
    public void handle(@NotNull ClientCommitMessage message, @NotNull WebSocketUser forUser) throws HandleException {
        final IPlayerState state = playerRepository.getPlayerState(forUser);

        if (state == null || !(state instanceof PlayingState)) {
            return;
        }

        state.processPacket(message, forUser);
    }
}
