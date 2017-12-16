package ru.glitchless.game.data;

import ru.glitchless.game.data.packages.toclient.FullSwapScene;
import ru.glitchless.game.data.packages.toclient.SnapObject;
import ru.glitchless.game.data.physics.Kirkle;
import ru.glitchless.game.data.physics.Platform;
import ru.glitchless.newserver.data.model.WebSocketUser;

public class EntityStorage {
    private Kirkle circle;
    private Platform firstPlatform;
    private Platform secondPlatform;

    public EntityStorage() {

    }

    public Kirkle getCircle() {
        return circle;
    }

    public void setCircle(Kirkle circle) {
        this.circle = circle;
    }

    public Platform getFirstPlatform() {
        return firstPlatform;
    }

    public void setFirstPlatform(Platform firstPlatform) {
        this.firstPlatform = firstPlatform;
    }

    public Platform getSecondPlatform() {
        return secondPlatform;
    }

    public void setSecondPlatform(Platform secondPlatform) {
        this.secondPlatform = secondPlatform;
    }

    public FullSwapScene fullSwapScene(WebSocketUser firstUser, WebSocketUser secondUser) {
        final FullSwapScene scene = new FullSwapScene();

        scene.put("kirkle", new SnapObject(circle));
        scene.put("platform_1", new SnapObject(firstPlatform)
                .setAdditionalInfo(firstUser
                        .getUserModel()
                        .getLogin()));

        scene.put("platform_2", new SnapObject(secondPlatform)
                .setAdditionalInfo(secondUser
                        .getUserModel()
                        .getLogin()));

        return scene; // Init all game element
    }
}
