package ru.glitchless.data.throwables;

public class ResourceException extends RuntimeException {
    public ResourceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourceException(String message) {
        super(message);
    }
}
