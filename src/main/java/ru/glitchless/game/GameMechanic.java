package ru.glitchless.game;

import ru.glitchless.base.data.IGameMechanic;
import ru.glitchless.server.data.models.game.toclient.FullSwapScene;

public class GameMechanic implements IGameMechanic {
    @Override
    public void tick(long elapsedMS) {

    }

    @Override
    public void onDestroy() {

    }

    public FullSwapScene firstSetting() {
        return new FullSwapScene(); // Init all game element
    }
}
