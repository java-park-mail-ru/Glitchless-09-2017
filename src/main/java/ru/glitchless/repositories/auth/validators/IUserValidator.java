package ru.glitchless.repositories.auth.validators;

import ru.glitchless.data.models.UserModel;

public interface IUserValidator {
    void validate(UserModel user);
}
