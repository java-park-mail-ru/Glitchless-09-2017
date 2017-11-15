package ru.glitchless.data.models.game.mechanics;

import ru.glitchless.data.models.game.toclient.FullSwapScene;

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
