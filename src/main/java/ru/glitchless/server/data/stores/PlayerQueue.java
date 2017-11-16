package ru.glitchless.server.data.stores;

import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;
import ru.glitchless.server.data.models.game.RoomUsers;
import ru.glitchless.server.data.models.WebSocketUser;
import ru.glitchless.server.data.throwables.HandleException;

import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class PlayerQueue {
    private ConcurrentLinkedQueue<WebSocketUser> userQueue = new ConcurrentLinkedQueue<>();

    public PlayerQueue() {

    }

    @Nullable
    public RoomUsers addUser(WebSocketUser user) {
        if (!user.getSession().isOpen()) {
            throw new HandleException("Can't pair user with close session");
        }

        if (userQueue.contains(user)) {
            throw new HandleException("User already in queue");
        }

        if (userQueue.isEmpty()) {
            userQueue.add(user);
            return null;
        }

        WebSocketUser waitingUser = userQueue.poll();
        while ((waitingUser == null || !waitingUser.getSession().isOpen()) && !userQueue.isEmpty()) {
            waitingUser = userQueue.poll();
        }

        if (waitingUser == null && userQueue.isEmpty()) {
            userQueue.add(user);
            return null;
        }

        return new RoomUsers(user, waitingUser);
    }
}
