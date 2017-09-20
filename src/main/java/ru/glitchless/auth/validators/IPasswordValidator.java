package ru.glitchless.auth.validators;

import ru.glitchless.throwables.InvalidData;

public interface IPasswordValidator {
    public boolean validate(String rawPassword) throws InvalidData;
}
