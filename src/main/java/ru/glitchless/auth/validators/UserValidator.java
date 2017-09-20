package ru.glitchless.auth.validators;

import ru.glitchless.models.UserModel;
import ru.glitchless.throwables.InvalidData;

public class UserValidator implements IUserValidator {
    private static final String LOGIN_PATTERN = "([A-Za-z0-9])+";
    private static final String EMAIL_PATTERN = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
    private IPasswordValidator validator;

    public UserValidator(IPasswordValidator validator) {
        this.validator = validator;
    }

    @Override
    public boolean validate(UserModel user) throws InvalidData {
        if (user.getLoginOrEmail() == null || user.getLoginOrEmail().isEmpty()) {
            throw new InvalidData("Login can't be empty");
        }
        if (!user.getLoginOrEmail().matches(LOGIN_PATTERN)) {
            throw new InvalidData("Invalid symblos! Login can be contains only [A-Za-z0-9]");
        }
        if (user.getEmail() != null && !user.getEmail().isEmpty() && user.getEmail().matches(EMAIL_PATTERN)) {
            throw new InvalidData("Invalid email!");
        }
        validator.validate(user.getPassword());
        return true;
    }
}
