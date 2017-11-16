package ru.glitchless.game.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.glitchless.server.utils.Constants;

public class Vector {
    private int diffX;
    private int diffY;

    public Vector(@JsonProperty("x") int diffX, @JsonProperty("y") int diffY) {
        this.diffX = diffX;
        this.diffY = diffY;
    }

    public int getDiffX() {
        return diffX;
    }

    public int getDiffY() {
        return diffY;
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
        int result = diffX;
        result = Constants.MAGIC_NUMBER * result + diffY;
        return result;
    }
}
