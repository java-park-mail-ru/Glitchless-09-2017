package ru.glitchless.newserver.interactor.auth;

import com.google.common.annotations.VisibleForTesting;
import org.springframework.stereotype.Component;
import ru.glitchless.newserver.data.model.UserModel;
import ru.glitchless.newserver.data.throwables.InvalidLoginOrPassword;
import ru.glitchless.newserver.repository.auth.UserRepository;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

@Component
public class UserInteractor {
    private static final String LOGIN_PATTERN = "([A-Za-z0-9])+";
    private final UserRepository userRepository;

    public UserInteractor(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private static boolean isValidEmail(String email) {
        boolean result = true;
        try {
            final InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }

    public UserModel registerUser(UserModel userModel) {
        validate(userModel);

        return userRepository.registerUser(userModel);
    }

    public UserModel authUser(UserModel userModel) {
        validate(userModel);

        return userRepository.authUser(userModel);
    }

    public UserModel changeUser(UserModel userModel) {
        validate(userModel);

        return userRepository.changeUser(userModel);
    }

    public boolean isContains(UserModel userModel) {
        validate(userModel);

        return userRepository.isContains(userModel);
    }

    private void validate(UserModel user) {
        if (user.getLogin() == null
                || user.getLogin().isEmpty()) {
            throw new InvalidLoginOrPassword("Login can't be empty");
        }
        if (!user.getLogin().matches(LOGIN_PATTERN)) {
            throw new InvalidLoginOrPassword("Invalid symblos! Login can be contains only [A-Za-z0-9]");
        }
        if (user.getEmail() != null && !user.getEmail().isEmpty()) {
            if (!isValidEmail(user.getEmail())) {
                throw new InvalidLoginOrPassword("Invalid email!");
            }
        }

        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new InvalidLoginOrPassword("Password can't be null");
        }
    }

    @VisibleForTesting
    public void validateForTest(UserModel userModel) {
        validate(userModel);
    }
}
