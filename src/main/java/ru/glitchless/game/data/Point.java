package ru.glitchless.game.data;

import com.fasterxml.jackson.annotation.JsonProperty;

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
}
