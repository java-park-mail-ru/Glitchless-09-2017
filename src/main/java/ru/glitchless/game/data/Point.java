package ru.glitchless.game.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.glitchless.game.collision.data.CollisionPoint;
import ru.glitchless.newserver.utils.Constants;

public class Point {
    private final float posX;
    private final float posY;

    public Point(@JsonProperty("x") float posX, @JsonProperty("y") float posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public float getPosX() {
        return posX;
    }

    public float getPosY() {
        return posY;
    }

    public Point plus(Point point) {
        return new Point(posX + point.posX,
                posY + point.posY);
    }

    public Point apply(Vector vector) {
        return new Point(posX + vector.getDiffX(),
                posY + vector.getDiffY());
    }

    public CollisionPoint toCollisionPoint() {
        return new CollisionPoint(posX, posY);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        final Point point = (Point) obj;

        return !(posX != point.posX) && posY == point.posY;
    }

    @Override
    public int hashCode() {
        float result = posX;
        result = Constants.MAGIC_NUMBER * result + posY;
        return (int) result;
    }
}
