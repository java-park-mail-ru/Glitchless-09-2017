package ru.glitchless.repositories.game;

import org.springframework.stereotype.Service;
import ru.glitchless.data.models.WebSocketMessage;
import ru.glitchless.data.models.fromclient.ClientState;
import ru.glitchless.data.models.game.RoomUsers;
import ru.glitchless.data.models.game.WebSocketUser;
import ru.glitchless.data.models.toclient.GameInitState;
import ru.glitchless.data.stores.PlayerQueue;
import ru.glitchless.data.throwables.HandleException;

@Service
public class LobbyService {
    private PlayerQueue playerQueue;

    public LobbyService(PlayerQueue playerQueue) {
        this.playerQueue = playerQueue;
    }

    public WebSocketMessage onInitUser(WebSocketUser user) {
        final RoomUsers users = playerQueue.addUser(user);
        if (users == null) {
            final GameInitState state = new GameInitState();
            state.setState(ClientState.WAITING_PLAYER.getId());
            return state;
        }
        return onFindLobby(users);
    }

    public WebSocketMessage onFindLobby(RoomUsers userPair) {
        throw new HandleException("Not implemented yet");
    }
}
