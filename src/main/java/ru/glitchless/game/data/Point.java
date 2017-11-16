package ru.glitchless.game.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.glitchless.server.utils.Constants;

public class Point {
    private int posX;
    private int posY;

    public Point(@JsonProperty("x") int posX, @JsonProperty("y") int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
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

        if (posX != point.posX) {
            return false;
        }
        return posY == point.posY;
    }

    @Override
    public int hashCode() {
        int result = posX;
        result = Constants.MAGIC_NUMBER * result + posY;
        return result;
    }
}
