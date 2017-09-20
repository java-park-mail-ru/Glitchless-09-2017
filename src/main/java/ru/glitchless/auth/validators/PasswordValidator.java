package ru.glitchless.auth.validators;

import ru.glitchless.throwables.InvalidData;

public class PasswordValidator implements IPasswordValidator {
    private static final String PASSWORD_PATTERN = "([A-Za-z0-9])+";
    private static final int MAX_LENGHT = 8;

    @Override
    public void validate(String rawPassword) throws InvalidData {
        if (rawPassword == null) {
            throw new InvalidData("Password can't be null");
        }
        if (rawPassword.length() < MAX_LENGHT) {
            throw new InvalidData("Small password length. Password must be between 8 characters");
        }

        if (!rawPassword.matches(PASSWORD_PATTERN)) {
            throw new InvalidData("Invalid symbols! Password can contains only [A-Za-z0-9]");
        }
    }
}
