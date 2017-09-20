package ru.glitchless.models;

public class Message {
    private Object message;
    private boolean successfull = true;

    public Message(Object message) {
        this.message = message;
    }

    public Message(Object message, boolean successfull) {
        this.message = message;
        this.successfull = successfull;
    }

    @SuppressWarnings("unused")
    public Object getMessage() {
        return message;
    }

    @SuppressWarnings("unused")
    public boolean isSuccessfull() {
        return successfull;
    }
}
