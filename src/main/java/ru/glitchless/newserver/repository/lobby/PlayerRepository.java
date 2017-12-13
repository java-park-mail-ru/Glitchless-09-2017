package ru.glitchless.newserver.repository.lobby;

import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Component;
import ru.glitchless.newserver.data.model.WebSocketUser;
import ru.glitchless.newserver.data.stores.PlayerStore;
import ru.glitchless.newserver.interactor.playerstate.IPlayerState;

@Component
public class PlayerRepository {
    private final PlayerStore playerStore;

    public PlayerRepository(PlayerStore playerStore) {
        this.playerStore = playerStore;
    }

    @Nullable
    public IPlayerState getPlayerState(WebSocketUser user) {
        return playerStore.getUserState(user);
    }

    public void putPlayerState(WebSocketUser user, IPlayerState state) {
        playerStore.putPlayer(user, state);
    }
}
