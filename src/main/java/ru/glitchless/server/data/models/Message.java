package ru.glitchless.server.data.models;

public class Message<T> {
    private T message;
    private boolean successful = true;

    public Message(boolean successful) {
        this.successful = successful;
        this.message = null;
    }

    public Message(boolean successful, T message) {
        this.message = message;
        this.successful = successful;
    }

    @SuppressWarnings("unused")
    public T getMessage() {
        return message;
    }

    @SuppressWarnings("unused")
    public boolean isSuccessful() {
        return successful;
    }
}
