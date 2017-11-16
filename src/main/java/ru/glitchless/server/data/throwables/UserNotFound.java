package ru.glitchless.server.data.throwables;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "User not found")
public class UserNotFound extends RuntimeException {
}
