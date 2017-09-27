package ru.glitchless.models.mappers;

import ru.glitchless.models.UserLocalModel;
import ru.glitchless.models.UserModel;

public class LocalUserMapperToServerModel implements Mapper<UserLocalModel, UserModel> {
    @Override
    public UserModel map(UserLocalModel localModel) {
        return new UserModel(localModel.getLogin(), null)
                .setEmail(localModel.getEmail());
    }
}
