package ru.glitchless.newserver.data.model;

public class Message<T> {
    private final T message;
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
