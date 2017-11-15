package ru.glitchless.repositories.auth;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.glitchless.data.models.UserModel;
import ru.glitchless.data.stores.UserDao;
import ru.glitchless.data.throwables.InvalidLoginOrPassword;
import ru.glitchless.repositories.auth.validators.UserValidator;

@Service
public class UserService {
    private final UserValidator validator;
    private final PasswordEncoder encoder;
    private final UserDao userDao;

    public UserService(PasswordEncoder passwordEncoder, UserValidator validator, UserDao userDao) {
        this.encoder = passwordEncoder;
        this.validator = validator;
        this.userDao = userDao;
    }

    public UserModel registerUser(UserModel userModel) {
        validator.validate(userModel);

        final UserModel userLocalModel = new UserModel(userModel.getLogin(),
                encoder.encode(userModel.getPassword()));
        userLocalModel.setEmail(userModel.getEmail());

        return userDao.addUser(userLocalModel);
    }

    public UserModel authUser(UserModel userModel) {
        validator.validate(userModel);

        final UserModel model = userDao.getUser(userModel.getLogin());

        if (model == null || !encoder.matches(userModel.getPassword(), model.getPassword())) {
            throw new InvalidLoginOrPassword();
        }

        return model;
    }

    public UserModel changeUser(UserModel userModel) {
        validator.validate(userModel);

        final UserModel model = userDao.getUser(userModel.getLogin());

        if (model == null || !encoder.matches(userModel.getPassword(), model.getPassword())) {
            throw new InvalidLoginOrPassword();
        }

        return userDao.updateUser(userModel.getLogin(), userModel.getEmail());
    }

    public boolean isContains(UserModel userModel) {
        validator.validate(userModel);

        final UserModel model = userDao.getUser(userModel.getLogin());

        return model != null;
    }
}
