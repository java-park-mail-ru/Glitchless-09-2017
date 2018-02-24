package ru.glitchless.game.physics;

import ru.glitchless.game.collision.CollisionManagerKt;
import ru.glitchless.game.collision.data.Arc;
import ru.glitchless.game.collision.data.CollisionPoint;
import ru.glitchless.game.data.EntityStorage;
import ru.glitchless.game.data.physics.Alien;
import ru.glitchless.game.data.physics.ForceField;
import ru.glitchless.game.data.physics.HealthBlock;
import ru.glitchless.game.data.physics.Platform;
import ru.glitchless.game.network.CollisionHandler;

public class CollisionLoop {
    private final EntityStorage entityStorage;
    private final CollisionHandler collisionHandler;

    public CollisionLoop(EntityStorage entityStorage, CollisionHandler collisionHandler) {
        this.entityStorage = entityStorage;
        this.collisionHandler = collisionHandler;
    }

    public void processCollisions(long elapsedMS) {
        processPlatform(entityStorage.getFirstPlatform(), elapsedMS);
        processPlatform(entityStorage.getSecondPlatform(), elapsedMS);
        processAlien(entityStorage.getAlien(), elapsedMS);
        entityStorage.getForceFields().forEach((field) -> this.processField(field, elapsedMS));
        entityStorage.getHealthBlockList().forEach((healthBlock -> this.processHpBlock(healthBlock, elapsedMS)));
    }

    private void processPlatform(Platform platform, long elapsedMS) {
        final Arc platformArc = platform.getArc();
        entityStorage.getLasers().forEach((laser) -> {
            final CollisionPoint[] points = CollisionManagerKt.getReflection(laser.getPoint().toCollisionPoint(),
                    laser.getSpeed().toPoint().toCollisionPoint(),
                    platformArc,
                    elapsedMS);
            if (points != null) {
                laser.onCollision(points, platform);
                collisionHandler.onLaserChange(laser);
            }

        });
    }

    private void processAlien(Alien alien, long elapsedMS) {
        entityStorage.getLasers().forEach((laser -> {
            if (!laser.isReflected()) {
                return;
            }

            final Object[] points = CollisionManagerKt.checkCollision(laser.getPoint().toCollisionPoint(),
                    laser.getSpeed().toPoint().toCollisionPoint(),
                    alien.getCircle(), elapsedMS, true, false);
            if (points != null) {
                laser.setForDestroy(true);
            }
        }));
    }

    private void processField(ForceField forceField, long elapsedMS) {
        if (forceField.isOff()) {
            return;
        }

        entityStorage.getLasers().forEach((laser -> {
            final Object[] points = CollisionManagerKt.checkCollision(laser.getPoint().toCollisionPoint(),
                    laser.getSpeed().toPoint().toCollisionPoint(),
                    forceField.getArc(), elapsedMS, false, false);
            if (points != null) {
                laser.setForDestroy(true);
                forceField.onCollision(points);
                collisionHandler.onFieldShot(forceField);
            }
        }));
    }

    private void processHpBlock(HealthBlock healthBlock, long elapsedMS) {
        entityStorage.getLasers().forEach((laser) -> {
            final Object[] points = CollisionManagerKt.checkCollision(laser.getPoint().toCollisionPoint(),
                    laser.getSpeed().toPoint().toCollisionPoint(),
                    healthBlock.getArc(), elapsedMS, false, false);
            if (points != null) {
                healthBlock.onCollision(points, collisionHandler);
                laser.setForDestroy(true);
            }
        });
    }
}
