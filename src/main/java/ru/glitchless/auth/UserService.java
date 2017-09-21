package ru.glitchless.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.glitchless.auth.validators.IUserValidator;
import ru.glitchless.models.UserLocalModel;
import ru.glitchless.models.UserModel;
import ru.glitchless.throwables.InvalidData;
import ru.glitchless.utils.IPropertiesFile;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserService {
    private final IPropertiesFile propertiesFile;
    private final IUserValidator validator;

    private ConcurrentHashMap<String, UserLocalModel> usersByLogin = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, UserLocalModel> usersBySession = new ConcurrentHashMap<>();

    @Autowired
    public UserService(IPropertiesFile propertiesFile, IUserValidator validator) {
        this.propertiesFile = propertiesFile;
        this.validator = validator;
    }

    public String registerUser(UserModel userModel) throws InvalidData {
        if (!validator.validate(userModel)) {
            return null;
        }

        if (usersByLogin.containsKey(userModel.getLogin())) {
            throw new InvalidData("User already exist");
        }

        final UserLocalModel userLocalModel = new UserLocalModel(userModel.getLogin(),
                userModel.getPassword(),
                propertiesFile.getSalt());
        userLocalModel.setEmail(userModel.getEmail());

        usersByLogin.put(userModel.getLogin(), userLocalModel);

        return getSession(userLocalModel);
    }

    public void logout(String session) {
        usersBySession.remove(session);
    }

    private String getSession(UserLocalModel localModel) {
        final String session = UUID.randomUUID().toString();
        usersBySession.put(session, localModel);
        return session;
    }

    public UserModel getUserBySession(String session) {
        final UserLocalModel user = usersBySession.get(session);

        if (user == null) {
            return null;
        }

        return new UserModel(user.getLoginOrEmail(), null).setEmail(user.getEmail());
    }

    public String authUser(UserModel userModel) throws InvalidData {
        if (!validator.validate(userModel)) {
            return null;
        }

        final UserLocalModel model = usersByLogin.get(userModel.getLogin());

        if (model == null || !model.comparePassword(userModel.getPassword())) {
            throw new InvalidData("Invalid login or password");
        }

        return getSession(model);
    }

    public UserModel changeUser(UserModel userModel) throws InvalidData {
        if (!validator.validate(userModel)) {
            return null;
        }

        final UserLocalModel model = usersByLogin.get(userModel.getLogin());

        if (model == null || !model.comparePassword(userModel.getPassword())) {
            throw new InvalidData("Invalid login or password");
        }

        model.setEmail(userModel.getEmail());

        final UserModel outputMode = new UserModel(userModel.getLogin(), null);
        outputMode.setEmail(userModel.getEmail());
        return outputMode;
    }
}
