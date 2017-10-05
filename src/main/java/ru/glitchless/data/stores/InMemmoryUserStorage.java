package ru.glitchless.data.stores;

import ru.glitchless.data.models.UserLocalModel;
import ru.glitchless.data.throwables.UserAlreadyExist;
import ru.glitchless.data.throwables.UserNotFound;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemmoryUserStorage {
    private Map<String, UserLocalModel> usersByLogin = new ConcurrentHashMap<>();

    public void addUser(UserLocalModel userLocalModel) {
        if (usersByLogin.putIfAbsent(userLocalModel.getLogin(), userLocalModel) != null) {
            throw new UserAlreadyExist();
        }
    }

    public UserLocalModel getUser(String login) {
        final UserLocalModel model = usersByLogin.get(login);
        if (model == null) {
            throw new UserNotFound();
        }
        return model;
    }
}
