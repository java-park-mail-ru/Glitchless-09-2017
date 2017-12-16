package ru.glitchless.newserver.data.stores;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Component;
import ru.glitchless.newserver.data.model.WebSocketUser;
import ru.glitchless.newserver.interactor.playerstate.IPlayerState;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class PlayerStore {
    private final ConcurrentHashMap<WebSocketUser, IPlayerState> playerStateMap = new ConcurrentHashMap<>();

    public void putPlayer(@NotNull WebSocketUser webSocketUser, @NotNull IPlayerState state) {
        playerStateMap.put(webSocketUser, state);
    }

    @Nullable
    public IPlayerState getUserState(WebSocketUser user) {
        return playerStateMap.get(user);
    }

    public List<WebSocketUser> getPlayersByState(IPlayerState state) {
        final List<WebSocketUser> users = new ArrayList<>();
        playerStateMap.forEach((key, value) -> {
            if (value.equals(state)) {
                users.add(key);
            }
        });
        return users;
    }

    public void removeUser(WebSocketUser webSocketUser) {
        playerStateMap.remove(webSocketUser);
    }
}
