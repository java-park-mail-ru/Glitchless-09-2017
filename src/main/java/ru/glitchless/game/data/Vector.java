package ru.glitchless.game.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.glitchless.server.utils.Constants;

public class Vector {
    @JsonProperty("x")
    private final float diffX;
    @JsonProperty("y")
    private final float diffY;

    public Vector(@JsonProperty("x") float diffX, @JsonProperty("y") float diffY) {
        this.diffX = diffX;
        this.diffY = diffY;
    }

    public float getDiffX() {
        return diffX;
    }

    public float getDiffY() {
        return diffY;
    }

    public Vector multipy(long multiplexer) {
        return new Vector(diffX * multiplexer,
                diffY * multiplexer);
    }

    public Point toPoint() {
        return new Point(diffX, diffY);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        final Vector vector = (Vector) obj;

        if (diffX != vector.diffX) {
            return false;
        }
        return diffY == vector.diffY;
    }

    @Override
    public int hashCode() {
        float result = diffX;
        result = Constants.MAGIC_NUMBER * result + diffY;
        return (int) result;
    }
}
