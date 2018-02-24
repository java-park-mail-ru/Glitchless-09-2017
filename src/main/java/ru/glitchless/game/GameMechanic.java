package ru.glitchless.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.glitchless.game.collision.data.Circle;
import ru.glitchless.game.data.EntityStorage;
import ru.glitchless.game.data.Point;
import ru.glitchless.game.data.ProcessingCommit;
import ru.glitchless.game.data.packages.fromclient.ClientCommitMessage;
import ru.glitchless.game.data.packages.toclient.FullSwapScene;
import ru.glitchless.game.data.packages.toclient.LightServerSnapMessage;
import ru.glitchless.game.data.packages.toclient.ServerSnapMessage;
import ru.glitchless.game.data.physics.*;
import ru.glitchless.game.data.physics.base.PhysicEntity;
import ru.glitchless.game.data.physics.base.PhysicObject;
import ru.glitchless.game.network.CollisionHandler;
import ru.glitchless.game.network.PacketHandlerManager;
import ru.glitchless.game.physics.CollisionLoop;
import ru.glitchless.game.physics.GameplayLoop;
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
    private final CollisionHandler collisionHandler;

    //Loops
    private final VectorToPointTick vectorTick;
    private final PacketHandlerManager packetHandlerManager;
    private final GameplayLoop gameplayLoop;
    private final CollisionLoop collisionLoop;


    public GameMechanic(RoomUsers roomUsers, SendMessageService sendMessageService) {
        this.roomUsers = roomUsers;
        this.sendMessageService = sendMessageService;
        this.vectorTick = new VectorToPointTick(physicEntities);
        this.packetHandlerManager = new PacketHandlerManager(idToObject);
        this.gameplayLoop = new GameplayLoop(roomUsers, sendMessageService, this);
        this.collisionHandler = new CollisionHandler(sendMessageService, roomUsers);
        this.collisionLoop = new CollisionLoop(entityStorage, collisionHandler);
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

        collisionLoop.processCollisions(elapsedMS);
        vectorTick.processTick(elapsedMS);
        gameplayLoop.processGameplay(elapsedMS);
        // TODO repair shield
        removeDestroyElement();
    }

    private void removeDestroyElement() {
        final List<PhysicObject> values = new ArrayList<>(idToObject.values());
        values.forEach(value -> {
            if (value.isForDestroy()) {
                value.destroy();
            }
        });
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
        final Point center =
                new Point(Constants.GAME_FIELD_SIZE.getPosX() / 2,
                        Constants.GAME_FIELD_SIZE.getPosY() / 2);

        final Kirkle circle = new Kirkle(center);
        putObject(circle);
        this.entityStorage.setCircle(circle);

        final Platform platform1 = new Platform(new Point(0, 0),
                roomUsers.getFirstUser(),
                circle);
        platform1.setRotation(Constants.GAME_START_PLATFORM1);
        putObject(platform1);
        this.entityStorage.setFirstPlatform(platform1);

        final Platform platform2 = new Platform(new Point(0, 0),
                roomUsers.getSecondUser(),
                circle);
        platform2.setRotation(Constants.GAME_START_PLATFORM2);
        putObject(platform2);
        this.entityStorage.setSecondPlatform(platform2);

        final int hpBlockCount = Constants.GAME_HP_COUNT * 2;
        for (int i = 0; i < hpBlockCount; i++) {
            final WebSocketUser player;
            if (i < Constants.GAME_HP_COUNT) {
                player = roomUsers.getFirstUser();
            } else {
                player = roomUsers.getSecondUser();
            }

            final HealthBlock hpBlock = new HealthBlock(new Point(0f, 0f),
                    new Circle(Constants.GAME_HP_CIRCLE_RADIUS, center.toCollisionPoint()),
                    player);

            hpBlock.setRotation(i * Constants.CIRCLE_ANGEL / hpBlockCount
                    + Constants.CIRCLE_ANGEL / hpBlockCount / 2);

            putObject(hpBlock);
            this.entityStorage.addHPBlock(hpBlock);
        }

        for (int i = 0; i < 2; i++) {
            final ForceField forceField = new ForceField(
                    new Point((float) (center.getPosX()
                            + Constants.CIRCLE_RADIUS * Constants.FORCEFIELD_ANGEL_COEFICIENT), center.getPosY()),
                    new Circle(Constants.GAME_FORCEFIELD_RADIUS, center.toCollisionPoint()));
            forceField.setRotation(Constants.CIRCLE_ANGEL / 4 + i * Constants.CIRCLE_ANGEL / 2);
            final Point coords = forceField.getPoint();
            forceField.setPoint(new Point(coords.getPosX(), coords.getPosY() - i));

            putObject(forceField);
            this.entityStorage.addForceField(forceField);
        }

        final Alien alien = new Alien(center);
        putObject(alien);
        this.entityStorage.setAlien(alien);
    }

    public void putObject(PhysicObject physicObject) {
        physicObject.setObjectId(idCounter.getAndIncrement());

        idToObject.put(physicObject.getObjectId(), physicObject);
        physicObject.subscribeOnDestroy((item) -> idToObject.remove(item.getObjectId()));

        if (physicObject instanceof PhysicEntity) {
            physicEntities.add((PhysicEntity) physicObject);
            physicObject.subscribeOnDestroy(item -> {
                physicEntities.remove(item);
            });
        }
    }


    @Override
    public boolean isDestroy() {
        return !(roomUsers.getFirstUser().getSession().isOpen()
                || roomUsers.getSecondUser().getSession().isOpen());
    }

    public EntityStorage getEntityStorage() {
        return entityStorage;
    }
}
