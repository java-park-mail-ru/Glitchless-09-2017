package ru.glitchless.repositories.auth.validators;

import ru.glitchless.data.throwables.InvalidLoginOrPassword;

public class PasswordValidator implements IPasswordValidator {
    private static final String PASSWORD_PATTERN = "([A-Za-z0-9])+";

    @Override
    public void validate(String rawPassword) {
        if (rawPassword == null) {
            throw new InvalidLoginOrPassword("Password can't be null");
        }

        if (!rawPassword.matches(PASSWORD_PATTERN)) {
            throw new InvalidLoginOrPassword("Invalid symbols! Password can contains only [A-Za-z0-9]");
        }
    }
}
