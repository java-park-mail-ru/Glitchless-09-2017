package ru.glitchless.server.data.throwables;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "For this action you need login.")
public class NeedAuthorization extends RuntimeException {
}
