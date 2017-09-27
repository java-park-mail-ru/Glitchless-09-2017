package ru.glitchless.auth.validators;

import ru.glitchless.models.UserModel;
import ru.glitchless.throwables.InvalidData;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class UserValidator implements IUserValidator {
    private static final String LOGIN_PATTERN = "([A-Za-z0-9])+";
    private IPasswordValidator validator;

    public UserValidator(IPasswordValidator pswdValidator) {
        this.validator = pswdValidator;
    }

    @Override
    public boolean validate(UserModel user) throws InvalidData {
        if (user.getLogin() == null
                || user.getLogin().isEmpty()) {
            throw new InvalidData("Login can't be empty");
        }
        if (!user.getLogin().matches(LOGIN_PATTERN)) {
            throw new InvalidData("Invalid symblos! Login can be contains only [A-Za-z0-9]");
        }
        if (user.getEmail() != null && !user.getEmail().isEmpty()) {
            if (!isValidEmail(user.getEmail())) {
                throw new InvalidData("Invalid email!");
            }
        }
        validator.validate(user.getPassword());
        return true;
    }

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
}
