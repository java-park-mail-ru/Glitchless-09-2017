package ru.glitchless.game.data;

public class Vector {
    private int diffX;
    private int diffY;

    public Vector(int diffX, int diffY) {
        this.diffX = diffX;
        this.diffY = diffY;
    }

    public int getDiffX() {
        return diffX;
    }

    public void setDiffX(int diffX) {
        this.diffX = diffX;
    }

    public int getDiffY() {
        return diffY;
    }

    public void setDiffY(int diffY) {
        this.diffY = diffY;
    }
}
