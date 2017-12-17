package ru.glitchless.newserver.data.throwables;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Invalid login or password")
public class InvalidLoginOrPassword extends RuntimeException {
    public InvalidLoginOrPassword(String message) {
        super(message);
    }

    public InvalidLoginOrPassword() {
        super();
    }
}
