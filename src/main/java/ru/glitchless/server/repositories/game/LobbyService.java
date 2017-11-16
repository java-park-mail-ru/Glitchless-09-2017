package ru.glitchless.server.repositories.game;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.glitchless.game.GameMechanic;
import ru.glitchless.game.data.packages.fromclient.ClientState;
import ru.glitchless.game.data.packages.toclient.GameInitState;
import ru.glitchless.server.data.models.WebSocketMessage;
import ru.glitchless.server.data.models.WebSocketUser;
import ru.glitchless.server.data.models.game.GameRoom;
import ru.glitchless.server.data.models.game.RoomUsers;
import ru.glitchless.server.data.stores.PlayerQueue;
import ru.glitchless.server.data.stores.RoomsStore;
import ru.glitchless.server.data.throwables.ServerIsFull;

import java.io.IOException;

@Service
public class LobbyService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LobbyService.class);

    private PlayerQueue playerQueue;
    private RoomsStore roomsStore;
    private ObjectMapper objectMapper;
    private RoomService roomService;
    private SendMessageService sendMessageService;

    public LobbyService(PlayerQueue playerQueue,
                        RoomsStore roomsStore,
                        ObjectMapper objectMapper,
                        RoomService roomService,
                        SendMessageService sendMessageService) {
        this.playerQueue = playerQueue;
        this.roomsStore = roomsStore;
        this.objectMapper = objectMapper;
        this.roomService = roomService;
        this.sendMessageService = sendMessageService;
        this.sendMessageService.init();
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

    private WebSocketMessage onFindLobby(RoomUsers userPair) {
        try {
            final GameRoom gameRoom = roomsStore.allocateMeRoom();
            roomService.putUserToRoom(userPair.getFirstUser(), gameRoom);
            roomService.putUserToRoom(userPair.getSecondUser(), gameRoom);
            return initRoom(gameRoom, userPair);
        } catch (ServerIsFull serverIsFull) {
            roomsStore.subscribeToResourceIsFree(() -> {
                if (!checkUserActive(userPair)) {
                    return;
                }
                final WebSocketMessage message = onFindLobby(userPair);
                userPair.sendToUsers(message, objectMapper);
            });

            final GameInitState state = new GameInitState();
            state.setState(ClientState.WAITING_ROOM.getId());
            return state;
        }
    }

    private boolean checkUserActive(RoomUsers userPair) {
        if (!userPair.isAllUserActive()) {
            if (userPair.getFirstUser().getSession().isOpen()) {
                playerQueue.addUser(userPair.getFirstUser());

                final GameInitState state = new GameInitState();
                state.setState(ClientState.WAITING_PLAYER.getId());

                try {
                    userPair.getFirstUser().sendToUser(state, objectMapper);
                } catch (IOException e) {
                    LOGGER.error("Error while send pocket 1", e);
                }
            }

            if (userPair.getSecondUser().getSession().isOpen()) {
                playerQueue.addUser(userPair.getSecondUser());

                final GameInitState state = new GameInitState();
                state.setState(ClientState.WAITING_PLAYER.getId());

                try {
                    userPair.getSecondUser().sendToUser(state, objectMapper);
                } catch (IOException e) {
                    LOGGER.error("Error while send pocket 2", e);
                }
            }
            return false;
        }
        return true;
    }

    private WebSocketMessage initRoom(GameRoom gameRoom, RoomUsers roomUsers) {
        final GameMechanic mechanic = new GameMechanic(roomUsers, sendMessageService);
        gameRoom.init(mechanic);

        final WebSocketMessage fullSwapScene = mechanic.firstSetting();
        roomUsers.sendToUsers(fullSwapScene, objectMapper);

        final GameInitState state = new GameInitState();
        state.setState(ClientState.READY.getId());
        return state;
    }
}