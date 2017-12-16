package ru.glitchless.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.glitchless.game.data.EntityStorage;
import ru.glitchless.game.data.Point;
import ru.glitchless.game.data.ProcessingCommit;
import ru.glitchless.game.data.packages.fromclient.ClientCommitMessage;
import ru.glitchless.game.data.packages.toclient.FullSwapScene;
import ru.glitchless.game.data.packages.toclient.LightServerSnapMessage;
import ru.glitchless.game.data.packages.toclient.ServerSnapMessage;
import ru.glitchless.game.data.physics.Kirkle;
import ru.glitchless.game.data.physics.Platform;
import ru.glitchless.game.data.physics.base.PhysicEntity;
import ru.glitchless.game.data.physics.base.PhysicObject;
import ru.glitchless.game.network.PacketHandlerManager;
import ru.glitchless.game.physics.VectorToPointTick;
import ru.glitchless.newserver.data.IGameMechanic;
import ru.glitchless.newserver.data.model.RoomUsers;
import ru.glitchless.newserver.data.model.WebSocketUser;
import ru.glitchless.newserver.utils.Constants;
import ru.glitchless.newserver.utils.Pair;
import ru.glitchless.newserver.utils.SendMessageService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class GameMechanic implements IGameMechanic {
    private static final Logger LOGGER = LoggerFactory.getLogger(GameMechanic.class);

    private final Queue<ProcessingCommit<ClientCommitMessage>> waitProcessCommit
            = new ConcurrentLinkedQueue<>();
    private final SendMessageService sendMessageService;
    private final RoomUsers roomUsers;

    private final List<PhysicEntity> physicEntities = new ArrayList<>();

    private final AtomicInteger idCounter = new AtomicInteger();
    private final HashMap<Integer, PhysicObject> idToObject = new HashMap<>();
    private final EntityStorage entityStorage = new EntityStorage();

    //Loops
    private final VectorToPointTick vectorTick;
    private final PacketHandlerManager packetHandlerManager;


    public GameMechanic(RoomUsers roomUsers, SendMessageService sendMessageService) {
        this.roomUsers = roomUsers;
        this.sendMessageService = sendMessageService;
        this.vectorTick = new VectorToPointTick(physicEntities);
        this.packetHandlerManager = new PacketHandlerManager(idToObject);
        this.firstSetting();
    }

    @Override
    @SuppressWarnings("OverlyBroadCatchBlock")
    public void tick(long elapsedMS) {
        ProcessingCommit<ClientCommitMessage> cmtMessage;
        while ((cmtMessage = waitProcessCommit.poll()) != null) {
            try {
                processMessage(cmtMessage);
            } catch (Exception e) {
                LOGGER.error("Error while process package", e);
            }
        }

        vectorTick.processTick(elapsedMS);
    }

    private void processMessage(ProcessingCommit<ClientCommitMessage> cmtMessage) {
        final Pair<LightServerSnapMessage, ServerSnapMessage> snapMessagePair
                = packetHandlerManager.processPacket(cmtMessage, cmtMessage.getUser());

        sendMessageService.sendMessage(snapMessagePair.getFirst(), cmtMessage.getUser());
        sendMessageService.sendMessage(snapMessagePair.getSecond(), roomUsers.getComanion(cmtMessage.getUser()));
    }

    @Override
    public void onNewPacket(ClientCommitMessage message, WebSocketUser webSocketUser) {
        waitProcessCommit.add(new ProcessingCommit<>(message, webSocketUser));
    }

    @Override
    public void onDestroy() {

    }

    public FullSwapScene dumpSwapScene() {
        return entityStorage.fullSwapScene(roomUsers.getFirstUser(), roomUsers.getSecondUser());
    }

    @Override
    public RoomUsers getPlayers() {
        return roomUsers;
    }

    private void firstSetting() {
        final Kirkle circle = new Kirkle(
                new Point(Constants.GAME_FIELD_SIZE.getPosX() / 2,
                        Constants.GAME_FIELD_SIZE.getPosY() / 2),
                idCounter.getAndIncrement());
        putObject(circle);
        this.entityStorage.setCircle(circle);

        final Platform platform1 = new Platform(new Point(0, 0),
                idCounter.getAndIncrement(),
                roomUsers.getFirstUser(),
                circle);
        platform1.setRotation(Constants.GAME_START_PLATFORM1);
        putObject(platform1);
        this.entityStorage.setFirstPlatform(platform1);

        final Platform platform2 = new Platform(new Point(0, 0),
                idCounter.getAndIncrement(),
                roomUsers.getSecondUser(),
                circle);
        platform2.setRotation(Constants.GAME_START_PLATFORM2);
        putObject(platform2);
        this.entityStorage.setSecondPlatform(platform2);
    }

    private void putObject(PhysicObject physicObject) {
        idToObject.put(physicObject.getObjectId(), physicObject);

        if (physicObject instanceof PhysicEntity) {
            physicEntities.add((PhysicEntity) physicObject);
        }
    }


    @Override
    public boolean isDestroy() {
        return !(roomUsers.getFirstUser().getSession().isOpen()
                || roomUsers.getSecondUser().getSession().isOpen());
    }
}
