package ru.glitchless.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.glitchless.game.data.Point;
import ru.glitchless.game.data.ProcessingCommit;
import ru.glitchless.game.data.packages.fromclient.ClientCommitMessage;
import ru.glitchless.game.data.packages.toclient.FullSwapScene;
import ru.glitchless.game.data.packages.toclient.LightServerSnapMessage;
import ru.glitchless.game.data.packages.toclient.ServerSnapMessage;
import ru.glitchless.game.data.packages.toclient.SnapObject;
import ru.glitchless.game.data.physics.Kirkle;
import ru.glitchless.game.data.physics.Platform;
import ru.glitchless.game.data.physics.base.PhysicEntity;
import ru.glitchless.game.data.physics.base.PhysicObject;
import ru.glitchless.game.physics.VectorToPointTick;
import ru.glitchless.server.data.models.IGameMechanic;
import ru.glitchless.server.data.models.WebSocketUser;
import ru.glitchless.server.data.models.game.RoomUsers;
import ru.glitchless.server.repositories.game.SendMessageService;
import ru.glitchless.server.utils.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class GameMechanic implements IGameMechanic {
    private static final Logger LOGGER = LoggerFactory.getLogger(GameMechanic.class);

    private Queue<ProcessingCommit<ClientCommitMessage>> waitProcessCommit
            = new ConcurrentLinkedQueue<>();
    private SendMessageService sendMessageService;
    private RoomUsers roomUsers;

    private List<PhysicEntity> physicEntities = new ArrayList<>();

    private AtomicInteger idCounter = new AtomicInteger();
    private HashMap<Integer, PhysicObject> idToObject = new HashMap<>();
    //Loops
    private VectorToPointTick vectorTick;

    public GameMechanic(RoomUsers roomUsers, SendMessageService sendMessageService) {
        this.roomUsers = roomUsers;
        this.sendMessageService = sendMessageService;
        this.vectorTick = new VectorToPointTick(physicEntities);
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
        if (cmtMessage.getMessage().isValidForUser(cmtMessage.getUser())) {
            final PhysicEntity physicEntity = (PhysicEntity) idToObject.get(cmtMessage.getMessage().getObjectId());
            physicEntity.setSpeed(cmtMessage.getMessage().getVector());

            final LightServerSnapMessage lightServerSnapMessage = new LightServerSnapMessage();
            lightServerSnapMessage.setCommitId(cmtMessage.getMessage().getCommitNumber());
            lightServerSnapMessage.setObjectId(cmtMessage.getMessage().getObjectId());
            lightServerSnapMessage.setPoint(physicEntity.getPoint());
            sendMessageService.sendMessage(lightServerSnapMessage, cmtMessage.getUser());

            final ServerSnapMessage serverSnapMessage = new ServerSnapMessage();
            serverSnapMessage.setObjectId(cmtMessage.getMessage().getObjectId());
            serverSnapMessage.setPoint(physicEntity.getPoint());
            serverSnapMessage.setVector(physicEntity.getSpeed());
            sendMessageService.sendMessage(serverSnapMessage, roomUsers.getComanion(cmtMessage.getUser()));
        }
    }

    @Override
    public void onNewPacket(ClientCommitMessage message, WebSocketUser webSocketUser) {
        waitProcessCommit.add(new ProcessingCommit<>(message, webSocketUser));
    }

    @Override
    public void onDestroy() {

    }

    public FullSwapScene firstSetting() {
        final FullSwapScene scene = new FullSwapScene();

        final Platform platform1 = new Platform(new Point(0, 0), idCounter.getAndIncrement());
        platform1.setRotation((float) Constants.GAME_START_PLATFORM1);
        idToObject.put(platform1.getObjectId(), platform1);
        scene.put("platform_1", new SnapObject(platform1));

        final Platform platform2 = new Platform(new Point(0, 0), idCounter.getAndIncrement());
        platform1.setRotation((float) Constants.GAME_START_PLATFORM1);
        idToObject.put(platform2.getObjectId(), platform2);
        scene.put("platform_2", new SnapObject(platform2));

        final Kirkle circle = new Kirkle(new Point(Constants.GAME_FIELD_SIZE.getPosX() / 2, Constants.GAME_FIELD_SIZE.getPosY() / 2), idCounter.getAndIncrement());
        idToObject.put(circle.getObjectId(), circle);
        scene.put("kirkle_1", new SnapObject(circle));

        return scene; // Init all game element
    }
}
