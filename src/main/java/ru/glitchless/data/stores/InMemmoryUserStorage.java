package ru.glitchless.data.stores;

import ru.glitchless.data.models.UserLocalModel;
import ru.glitchless.data.throwables.UserAlreadyExist;
import ru.glitchless.data.throwables.UserNotFound;

import java.util.concurrent.ConcurrentHashMap;

public class InMemmoryUserStorage {
    private ConcurrentHashMap<String, UserLocalModel> usersByLogin = new ConcurrentHashMap<>();

    public void addUser(UserLocalModel userLocalModel) {
        if (usersByLogin.get(userLocalModel.getLogin()) != null) {
            throw new UserAlreadyExist();
        }
        usersByLogin.put(userLocalModel.getLogin(), userLocalModel);
    }

    public UserLocalModel getUser(String login) {
        final UserLocalModel model = usersByLogin.get(login);
        if (model == null) {
            throw new UserNotFound();
        }
        return model;
    }
}
