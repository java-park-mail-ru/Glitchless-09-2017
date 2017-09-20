package ru.glitchless.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.glitchless.auth.validators.IUserValidator;
import ru.glitchless.models.UserLocalModel;
import ru.glitchless.models.UserModel;
import ru.glitchless.throwables.UserAlreadyExist;
import ru.glitchless.utils.IPropertiesFile;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserService {

    @Autowired
    IPropertiesFile propertiesFile;

    @Autowired
    IUserValidator validator;

    private ConcurrentHashMap<String, UserLocalModel> usersByLogin = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, UserLocalModel> usersBySession = new ConcurrentHashMap<>();

    /**
     * @param userModel New user
     * @return session Null when user already register and not-null if user sucs register
     */
    public String registerUser(UserModel userModel) throws UserAlreadyExist {
        if (usersByLogin.containsKey(userModel.getLoginOrEmail())) {
            throw new UserAlreadyExist();
        }

        UserLocalModel userLocalModel = new UserLocalModel(userModel.getLoginOrEmail(),
                userModel.getPassword(),
                propertiesFile.getSalt());

        usersByLogin.put(userModel.getLoginOrEmail(), userLocalModel);
        return null;
    }
}
