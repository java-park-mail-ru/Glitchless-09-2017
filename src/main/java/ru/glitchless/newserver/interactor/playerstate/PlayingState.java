package ru.glitchless.newserver.interactor.playerstate;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.glitchless.game.data.packages.fromclient.ClientCommitMessage;
import ru.glitchless.game.data.packages.fromclient.WantPlayMessage;
import ru.glitchless.game.data.packages.toclient.GameInitState;
import ru.glitchless.newserver.data.IGameMechanic;
import ru.glitchless.newserver.data.model.ClientState;
import ru.glitchless.newserver.data.model.RoomUsers;
import ru.glitchless.newserver.data.model.WebSocketMessage;
import ru.glitchless.newserver.data.model.WebSocketUser;
import ru.glitchless.newserver.data.stores.GameStore;
import ru.glitchless.newserver.utils.SendMessageService;

public class PlayingState implements IPlayerState {
    private final GameStore gameStore;
    private final SendMessageService sendMessageService;

    public PlayingState(GameStore gameStore, SendMessageService sendMessageService) {
        this.gameStore = gameStore;
        this.sendMessageService = sendMessageService;
    }

    @Override
    public void processPacket(@NotNull WebSocketMessage message, @Nullable WebSocketUser forUser) {
        if (forUser == null) {
            return;
        }

        checkAndReplaceOnCurrentSession(forUser);

        if (message instanceof ClientCommitMessage) {
            gameStore.getGameMechanic().onNewPacket((ClientCommitMessage) message, forUser);
        }

        if (message instanceof WantPlayMessage) {
            final IGameMechanic gameMechanic = gameStore.getGameMechanic();
            if (gameMechanic != null) {
                sendMessageService.sendMessage(gameMechanic.dumpSwapScene(), forUser);
            }
        }
    }

    private void checkAndReplaceOnCurrentSession(WebSocketUser webSocketUser) {
        final RoomUsers roomUsers = gameStore.getGameMechanic().getPlayers();

        if (roomUsers.getFirstUser().getUserModel().equals(webSocketUser.getUserModel())) {
            roomUsers.getFirstUser().replaceSession(webSocketUser.getSession());
        }

        if (roomUsers.getSecondUser().getUserModel().equals(webSocketUser.getUserModel())) {
            roomUsers.getSecondUser().replaceSession(webSocketUser.getSession());
        }
    }

    @Override
    public GameInitState getMessageForState() {
        final GameInitState gameInitState = new GameInitState();
        gameInitState.setState(ClientState.PLAYING.getId());
        return gameInitState;
    }
}
