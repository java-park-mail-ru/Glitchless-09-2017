package ru.glitchless.repositories.auth.validators;

public interface IPasswordValidator {
    void validate(String rawPassword);
}
