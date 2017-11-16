package ru.glitchless.base.data;

public interface IGameMechanic {

    void tick(long elapsedMS);

    void onDestroy();
}
