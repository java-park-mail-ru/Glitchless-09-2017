package ru.glitchless.data.mappers;

import ru.glitchless.data.models.UserLocalModel;
import ru.glitchless.data.models.UserModel;

public class LocalUserMapperToServerModel implements Mapper<UserLocalModel, UserModel> {
    @Override
    public UserModel map(UserLocalModel localModel) {
        return new UserModel(
                localModel.getLogin(),
                localModel.getPasswordBCrypt())
                    .setEmail(localModel.getEmail());
    }
}
