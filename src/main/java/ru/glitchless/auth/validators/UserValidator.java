package ru.glitchless.auth.validators;

import ru.glitchless.models.UserModel;
import ru.glitchless.throwables.InvalidData;

public class UserValidator implements IUserValidator{
    private static final String LOGIN_PATTERN = "([A-Za-z0-9@.])+";
    private IPasswordValidator validator;

    public UserValidator(IPasswordValidator validator) {
        this.validator = validator;
    }

    public boolean validate(UserModel user) throws InvalidData {
        if (user.getLoginOrEmail() == null || user.getLoginOrEmail().isEmpty()) {
            throw new InvalidData("Login can't be empty");
        }
        if (!user.getLoginOrEmail().matches(LOGIN_PATTERN)) {
            throw new InvalidData("Invalid symblos! Login can be contains only [A-Za-z0-9@.]");
        }
        return true;
    }
}
