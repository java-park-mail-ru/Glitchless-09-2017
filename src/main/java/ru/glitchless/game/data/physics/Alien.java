package ru.glitchless.game.data.physics;

import ru.glitchless.game.collision.data.Circle;
import ru.glitchless.game.data.Point;
import ru.glitchless.game.data.physics.base.PhysicObject;
import ru.glitchless.newserver.utils.Constants;

public class Alien extends PhysicObject {
    private final Circle circle;

    public Alien(Point point) {
        super(point);
        this.circle = new Circle(Constants.GAME_ALIEN_SIZE / 2, point.toCollisionPoint());
    }

    public Circle getCircle() {
        return circle;
    }
}
