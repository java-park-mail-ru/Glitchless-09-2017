package ru.glitchless.game.data.physics.base;

import ru.glitchless.game.data.Point;
import ru.glitchless.game.data.Vector;

public class PhysicEntity extends PhysicObject {
    private Vector speed = new Vector(0, 0);
    private float rotationSpeed = 0;

    public PhysicEntity(Point point) {
        super(point);
    }

    public Vector getSpeed() {
        return speed;
    }

    public void setSpeed(Vector speed) {
        this.speed = speed;
    }

    public float getRotationSpeed() {
        return rotationSpeed;
    }

    public void setRotationSpeed(float rotationSpeed) {
        this.rotationSpeed = rotationSpeed;
    }
}
