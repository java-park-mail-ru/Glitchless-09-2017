package ru.glitchless.server.data.stores;

import com.google.common.collect.Sets;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;
import ru.glitchless.newserver.data.model.WebSocketUser;
import ru.glitchless.server.data.models.game.RoomUsers;
import ru.glitchless.newserver.data.throwables.HandleException;

import java.util.Set;

@Service
public class PlayerQueue {
    private Set<WebSocketUser> userQueue = Sets.newConcurrentHashSet();

    public PlayerQueue() {

    }

    @Nullable
    public RoomUsers addUser(WebSocketUser user) {
        if (!user.getSession().isOpen()) {
            throw new HandleException("Can't pair user with close session");
        }

        if (!userQueue.add(user)) {
            throw new HandleException("User already in queue");
        }
        userQueue.remove(user);

        WebSocketUser waitingUser = poolFromSet(userQueue);

        while ((waitingUser == null || !waitingUser.getSession().isOpen()) && !userQueue.isEmpty()) {
            waitingUser = poolFromSet(userQueue);
        }

        if (waitingUser == null && userQueue.isEmpty()) {
            userQueue.add(user);
            return null;
        }

        return new RoomUsers(user, waitingUser);
    }

    @Nullable
    private WebSocketUser poolFromSet(Set<WebSocketUser> set) {
        for (WebSocketUser user : set) {
            if (set.remove(user)) {
                return user;
            }
        }
        return null;
    }
}
