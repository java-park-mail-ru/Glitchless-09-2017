package ru.glitchless.game.data.packages.toclient;


import ru.glitchless.game.data.physics.base.PhysicEntity;
import ru.glitchless.game.data.physics.base.PhysicObject;

public class CreateObjectMessage extends ServerSnapMessage {
    private String objectType;

    public CreateObjectMessage() {
        super();
        setType("CreateObjectMessage");
    }

    public CreateObjectMessage(PhysicObject physicObject) {
        super(physicObject);
        setType("CreateObjectMessage");
        this.objectType = physicObject.getClass().getSimpleName();
    }

    public CreateObjectMessage(PhysicEntity physicEntity) {
        super(physicEntity);
        setType("CreateObjectMessage");
        this.objectType = physicEntity.getClass().getSimpleName();
    }

    public String getObjectType() {
        return objectType;
    }
}
