package ru.glitchless.game.data;

import com.fasterxml.jackson.annotation.JsonProperty;

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
}
