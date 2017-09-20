package ru.glitchless.auth.validators;

import ru.glitchless.throwables.InvalidData;

public interface IPasswordValidator {
    void validate(String rawPassword) throws InvalidData;
}
