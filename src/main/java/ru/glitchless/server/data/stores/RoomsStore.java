package ru.glitchless.server.data.stores;

import org.springframework.stereotype.Service;
import ru.glitchless.server.data.models.game.GameRoom;
import ru.glitchless.server.data.models.game.IWaitAllocateResource;
import ru.glitchless.server.data.throwables.ServerIsFull;
import ru.glitchless.server.utils.Constants;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class RoomsStore {
    private Queue<IWaitAllocateResource> resourcesListener = new ConcurrentLinkedQueue<>();
    private List<GameRoom> rooms = new CopyOnWriteArrayList<>();
    private AtomicInteger roomId = new AtomicInteger();

    public RoomsStore() {

    }

    public GameRoom allocateMeRoom() {
        if (rooms.size() >= Constants.MAX_ROOMS_COUNT) {
            throw new ServerIsFull("ServerIsFull ¯\\_(ツ*_*)_/¯");
        }
        final GameRoom room = new GameRoom(roomId.getAndIncrement());
        rooms.add(room);
        room.addDestroyListener(() -> {
            rooms.remove(room);
            while (rooms.size() < Constants.MAX_ROOMS_COUNT) {
                resourcesListener.poll().onCanAllocateResource();
            }
        });
        return room;
    }

    public void subscribeToResourceIsFree(IWaitAllocateResource listener) {
        resourcesListener.add(listener);
    }
}
