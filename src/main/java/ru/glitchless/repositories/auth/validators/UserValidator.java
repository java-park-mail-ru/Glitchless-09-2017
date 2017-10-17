package ru.glitchless.repositories.auth.validators;

import ru.glitchless.data.models.UserModel;
import ru.glitchless.data.throwables.InvalidLoginOrPassword;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class UserValidator {
    private static final String LOGIN_PATTERN = "([A-Za-z0-9])+";

    static boolean isValidEmail(String email) {
        boolean result = true;
        try {
            final InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }

    public void validate(UserModel user) {
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
}
