package ru.glitchless.server.repositories.game;

import org.springframework.stereotype.Component;
import ru.glitchless.server.data.models.WebSocketUser;
import ru.glitchless.server.data.models.game.GameRoom;
import ru.glitchless.server.data.stores.RoomsStore;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RoomService {
    private Map<WebSocketUser, GameRoom> userToRoom = new ConcurrentHashMap<>();
    private RoomsStore roomsStore;

    public RoomService(RoomsStore store) {
        this.roomsStore = store;
    }

    public GameRoom getRoomByUser(WebSocketUser user) {
        return userToRoom.get(user);
    }

    public void putUserToRoom(WebSocketUser user, GameRoom room) {
        userToRoom.put(user, room);
        room.addDestroyListener(() -> {
            if (Objects.equals(userToRoom.get(user), room)) {
                userToRoom.remove(user);
            }
        });

    }
}
