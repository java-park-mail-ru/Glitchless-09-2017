package ru.glitchless.throwables;

public class InvalidData extends RuntimeException {
    private String reason;

    public InvalidData(String reason) {
        super(reason);
        this.reason = reason;
    }
}
