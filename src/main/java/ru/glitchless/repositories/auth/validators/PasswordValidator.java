package ru.glitchless.repositories.auth.validators;

import ru.glitchless.data.throwables.InvalidLoginOrPassword;

public class PasswordValidator implements IPasswordValidator {

    @Override
    public void validate(String rawPassword) {
        if (rawPassword == null) {
            throw new InvalidLoginOrPassword("Password can't be null");
        }
    }
}
