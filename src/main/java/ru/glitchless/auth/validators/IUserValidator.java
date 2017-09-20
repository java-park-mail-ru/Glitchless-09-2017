package ru.glitchless.auth.validators;

import ru.glitchless.models.UserModel;
import ru.glitchless.throwables.InvalidData;

public interface IUserValidator {
    public boolean validate(UserModel user) throws InvalidData;
}
