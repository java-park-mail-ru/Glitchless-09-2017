package ru.glitchless.game.data.physics;

import ru.glitchless.game.data.Point;
import ru.glitchless.game.data.Vector;
import ru.glitchless.game.data.physics.base.PhysicEntity;

public class Platform extends PhysicEntity {
    public Platform(Point point, int objectId) {
        super(point, objectId);
    }
}
