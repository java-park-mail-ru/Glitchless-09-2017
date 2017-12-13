package ru.glitchless.newserver.repository.lobby;

import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Component;
import ru.glitchless.newserver.data.model.WebSocketUser;
import ru.glitchless.newserver.data.stores.PlayerStore;
import ru.glitchless.newserver.interactor.playerstate.IPlayerState;
import ru.glitchless.newserver.utils.SendMessageService;

import java.util.List;

@Component
public class PlayerRepository {
    private final PlayerStore playerStore;
    private final SendMessageService sendMessageService;

    public PlayerRepository(PlayerStore playerStore, SendMessageService sendMessageService) {
        this.playerStore = playerStore;
        this.sendMessageService = sendMessageService;
    }

    @Nullable
    public IPlayerState getPlayerState(WebSocketUser user) {
        return playerStore.getUserState(user);
    }

    public void putPlayerState(WebSocketUser user, IPlayerState state) {
        playerStore.putPlayer(user, state);
        sendMessageService.sendMessageSync(state.getMessageForState(), user);
    }

    @Nullable
    public WebSocketUser getPlayerWithState(@Nullable WebSocketUser currentUser, IPlayerState state) {
        // TODO BUG. Race condition.
        final List<WebSocketUser> users = playerStore.getPlayersByState(state);
        users.remove(currentUser);

        if (users.isEmpty()) {
            return null;
        }

        return users.get(0);
    }
}
