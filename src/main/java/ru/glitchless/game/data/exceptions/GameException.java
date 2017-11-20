package ru.glitchless.game.data.exceptions;

public class GameException extends RuntimeException {
    public GameException(String message){
        super(message);
    }


    public GameException(String message, Exception e){
        super(message, e);
    }
}
