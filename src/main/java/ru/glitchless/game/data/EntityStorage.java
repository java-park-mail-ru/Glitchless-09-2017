package ru.glitchless.game.data;

import ru.glitchless.game.data.packages.toclient.FullSwapScene;
import ru.glitchless.game.data.packages.toclient.SnapObject;
import ru.glitchless.game.data.physics.*;
import ru.glitchless.newserver.data.model.WebSocketUser;

import java.util.ArrayList;
import java.util.List;

public class EntityStorage {
    private final List<HealthBlock> healthBlockList = new ArrayList<>();
    private final List<ForceField> forceFields = new ArrayList<>();
    private final List<Laser> lasers = new ArrayList<>();
    private Kirkle circle;
    private Platform firstPlatform;
    private Platform secondPlatform;
    private Alien alien;

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

    public Alien getAlien() {
        return alien;
    }

    public void setAlien(Alien alien) {
        this.alien = alien;
    }

    public void addHPBlock(HealthBlock healthBlock) {
        this.healthBlockList.add(healthBlock);
        healthBlockList.sort((item1, item2) -> (int) (item1.getRotation() - item2.getRotation()));
        healthBlock.subscribeOnDestroy((item) -> {
            this.healthBlockList.remove(item);
        });
    }

    public void addForceField(ForceField forceField) {
        this.forceFields.add(forceField);

        forceFields.sort((item1, item2) -> (int) (item1.getRotation() - item2.getRotation()));
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

        scene.put("alien", new SnapObject(alien));

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

    public List<HealthBlock> getHealthBlockList() {
        return healthBlockList;
    }

    public List<ForceField> getForceFields() {
        return forceFields;
    }

    public void addLaser(Laser laser) {
        this.lasers.add(laser);
        laser.subscribeOnDestroy((item) -> {
            this.lasers.remove(item);
        });
    }

    public List<Laser> getLasers() {
        return lasers;
    }
}
