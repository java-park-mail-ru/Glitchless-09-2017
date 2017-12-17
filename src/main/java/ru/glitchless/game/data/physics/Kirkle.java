package ru.glitchless.game.data.physics;

import ru.glitchless.game.data.Point;
import ru.glitchless.game.data.physics.base.PhysicObject;
import ru.glitchless.newserver.utils.Constants;

public class Kirkle extends PhysicObject {
    private int radius = Constants.CIRCLE_RADIUS;

    public Kirkle(Point point, int objectId) {
        super(point, objectId);
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }
}
