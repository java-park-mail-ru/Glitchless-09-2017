package ru.glitchless.models;

public class Message {
    private Object message;
    private boolean successful = true;

    public Message(Object message) {
        this.message = message;
    }

    public Message(Object message, boolean successful) {
        this.message = message;
        this.successful = successful;
    }

    @SuppressWarnings("unused")
    public Object getMessage() {
        return message;
    }

    @SuppressWarnings("unused")
    public boolean isSuccessful() {
        return successful;
    }
}
