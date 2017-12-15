package ru.glitchless.newserver.data.throwables;

public class HandleException extends RuntimeException {
    public HandleException(String message, Throwable cause) {
        super(message, cause);
    }

    public HandleException(String message) {
        super(message);
    }
}
