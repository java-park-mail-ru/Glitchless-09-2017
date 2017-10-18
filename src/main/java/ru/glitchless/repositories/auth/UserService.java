package ru.glitchless.repositories.auth;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.glitchless.data.models.UserLocalModel;
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

    public UserLocalModel registerUser(UserModel userModel) {
        validator.validate(userModel);

        final UserLocalModel userLocalModel = new UserLocalModel(userModel.getLogin(),
                encoder.encode(userModel.getPassword()));
        userLocalModel.setEmail(userModel.getEmail());

        return userDao.addUser(userLocalModel);
    }

    public UserLocalModel authUser(UserModel userModel) {
        validator.validate(userModel);

        final UserLocalModel model = userDao.getUser(userModel.getLogin());

        if (model == null || !encoder.matches(userModel.getPassword(), model.getPasswordBCrypt())) {
            throw new InvalidLoginOrPassword();
        }

        return model;
    }

    public UserLocalModel changeUser(UserModel userModel) {
        validator.validate(userModel);

        final UserLocalModel model = userDao.getUser(userModel.getLogin());

        if (model == null || !encoder.matches(userModel.getPassword(), model.getPasswordBCrypt())) {
            throw new InvalidLoginOrPassword();
        }

        model.setEmail(userModel.getEmail());

        return userDao.updateUser(userModel.getLogin(), userModel.getEmail());
    }
}
