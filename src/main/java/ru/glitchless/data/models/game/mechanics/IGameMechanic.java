package ru.glitchless.data.models.game.mechanics;

public interface IGameMechanic {

    void tick(long elapsedMS);

    void onDestroy();
}
