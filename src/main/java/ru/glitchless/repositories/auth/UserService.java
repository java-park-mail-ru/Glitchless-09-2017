package ru.glitchless.repositories.auth;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.glitchless.data.models.UserLocalModel;
import ru.glitchless.data.models.UserModel;
import ru.glitchless.data.stores.InMemmoryUserStorage;
import ru.glitchless.data.throwables.InvalidLoginOrPassword;
import ru.glitchless.repositories.auth.validators.UserValidator;

@Service
public class UserService {
    private final UserValidator validator;
    private final PasswordEncoder encoder;

    private InMemmoryUserStorage userStorage = new InMemmoryUserStorage();

    public UserService(PasswordEncoder passwordEncoder, UserValidator validator) {
        this.encoder = passwordEncoder;
        this.validator = validator;
    }

    public UserLocalModel registerUser(UserModel userModel) {
        validator.validate(userModel);

        final UserLocalModel userLocalModel = new UserLocalModel(userModel.getLogin(),
                encoder.encode(userModel.getPassword()));
        userLocalModel.setEmail(userModel.getEmail());

        userStorage.addUser(userLocalModel);

        return userLocalModel;
    }

    public UserLocalModel authUser(UserModel userModel) {
        validator.validate(userModel);

        final UserLocalModel model = userStorage.getUser(userModel.getLogin());

        if (model == null || !encoder.matches(userModel.getPassword(), model.getPasswordBCrypt())) {
            throw new InvalidLoginOrPassword();
        }

        return model;
    }

    public UserModel changeUser(UserModel userModel) {
        validator.validate(userModel);

        final UserLocalModel model = userStorage.getUser(userModel.getLogin());

        if (model == null || !encoder.matches(userModel.getPassword(), model.getPasswordBCrypt())) {
            throw new InvalidLoginOrPassword();
        }

        model.setEmail(userModel.getEmail());

        final UserModel outputMode = new UserModel(userModel.getLogin(), null);
        outputMode.setEmail(userModel.getEmail());

        return outputMode;
    }
}
