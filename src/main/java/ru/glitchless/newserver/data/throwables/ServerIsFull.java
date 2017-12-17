package ru.glitchless.newserver.data.throwables;

public class ServerIsFull extends RuntimeException {
    public ServerIsFull(String message) {
        super(message);
    }

    public ServerIsFull(String message, Throwable cause) {
        super(message, cause);
    }
}
