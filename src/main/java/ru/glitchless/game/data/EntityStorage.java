package ru.glitchless.game.data;

import ru.glitchless.game.data.packages.toclient.FullSwapScene;
import ru.glitchless.game.data.packages.toclient.SnapObject;
import ru.glitchless.game.data.physics.ForceField;
import ru.glitchless.game.data.physics.HealthBlock;
import ru.glitchless.game.data.physics.Kirkle;
import ru.glitchless.game.data.physics.Platform;
import ru.glitchless.newserver.data.model.WebSocketUser;

import java.util.ArrayList;
import java.util.List;

public class EntityStorage {
    private Kirkle circle;
    private Platform firstPlatform;
    private Platform secondPlatform;
    private List<HealthBlock> healthBlockList = new ArrayList<>();
    private List<ForceField> forceFields = new ArrayList<>();

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

    public void addHPBlock(HealthBlock healthBlock) {
        this.healthBlockList.add(healthBlock);
    }

    public void addForceField(ForceField forceField) {
        this.forceFields.add(forceField);
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

        healthBlockList.sort((item1, item2) -> (int) (item1.getRotation() - item2.getRotation()));

        forceFields.sort((item1, item2) -> (int) (item1.getRotation() - item2.getRotation()));

        final SnapObject[] healthSnap = new SnapObject[healthBlockList.size()];

        for (int i = 0; i < healthSnap.length; i++) {
            healthSnap[i] = new SnapObject(healthBlockList.get(i));
        }

        final SnapObject[] forceSnap = new SnapObject[forceFields.size()];

        for (int i = 0; i < forceSnap.length; i++) {
            forceSnap[i] = new SnapObject(forceFields.get(i));
        }

        scene.put("hpblock", healthSnap);
        scene.put("forceFields", forceSnap);

        return scene; // Init all game element
    }
}
