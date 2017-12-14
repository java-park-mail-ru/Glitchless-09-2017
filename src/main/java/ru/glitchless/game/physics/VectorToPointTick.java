package ru.glitchless.game.physics;

import ru.glitchless.game.data.Point;
import ru.glitchless.game.data.physics.base.PhysicEntity;
import ru.glitchless.newserver.utils.Constants;

import java.util.List;

public class VectorToPointTick {
    private final List<PhysicEntity> physicEntityList;

    public VectorToPointTick(List<PhysicEntity> physicEntityList) {
        this.physicEntityList = physicEntityList;
    }

    public void processTick(long elapsedMS) {
        physicEntityList.forEach((item) -> {
            final float newX = item.getPoint().getPosX() + item.getSpeed().getDiffX() * elapsedMS;
            final float newY = item.getPoint().getPosY() + item.getSpeed().getDiffY() * elapsedMS;
            item.setPoint(new Point(newX, newY));

            final float newRotation = item.getRotation() + item.getRotationSpeed() * elapsedMS;
            item.setRotation(newRotation);

            if (isMarkDestroyed(newX, newY)) {
                item.setForDestroy(true);
            }
        });
    }

    private boolean isMarkDestroyed(float newX, float newY) {
        return newX > Constants.GAME_FIELD_SIZE.getPosX()
                || newY > Constants.GAME_FIELD_SIZE.getPosY()
                || newX < 0
                || newY < 0;
    }
}
