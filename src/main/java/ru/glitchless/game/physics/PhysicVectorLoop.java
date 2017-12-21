package ru.glitchless.game.physics;

import ru.glitchless.game.collision.CollisionManagerKt;
import ru.glitchless.game.collision.data.Arc;
import ru.glitchless.game.collision.data.CollisionPoint;
import ru.glitchless.game.data.EntityStorage;
import ru.glitchless.game.data.physics.Platform;

public class PhysicVectorLoop {
    private final EntityStorage entityStorage;

    public PhysicVectorLoop(EntityStorage entityStorage) {
        this.entityStorage = entityStorage;
    }

    void processCollisions(long elapsedMS) {
        processPlatform(entityStorage.getFirstPlatform(), elapsedMS);
        processPlatform(entityStorage.getSecondPlatform(), elapsedMS);
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
            }

        });
    }

}
