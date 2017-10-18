package ru.glitchless.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.glitchless.data.models.Message;
import ru.glitchless.data.models.UserModel;
import ru.glitchless.data.throwables.InvalidLoginOrPassword;
import ru.glitchless.data.throwables.NeedAuthorization;
import ru.glitchless.repositories.auth.UserService;
import ru.glitchless.utils.Constants;

import javax.servlet.http.HttpSession;

@RestController
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/api/signup")
    public ResponseEntity<Message> signUp(@RequestBody(required = false) UserModel userModel, HttpSession httpSession) {
        if (userModel == null) {
            throw new InvalidLoginOrPassword("Empty field: password or login");
        }

        final UserModel model = service.registerUser(userModel);
        if (model != null) {
            httpSession.setAttribute(Constants.SESSION_EXTRA_USER, model);
            return ResponseEntity.ok(new Message<>(true, model));
        } else {
            return ResponseEntity.badRequest().body(new Message(false));
        }
    }

    @PostMapping("/api/login")
    public ResponseEntity<Message> login(@RequestBody(required = false) UserModel userModel, HttpSession httpSession) {
        if (userModel == null) {
            throw new InvalidLoginOrPassword("Empty field: password or login");
        }

        final UserModel model = service.authUser(userModel);
        if (model != null) {
            httpSession.setAttribute(Constants.SESSION_EXTRA_USER, model);
            return ResponseEntity.ok(new Message<>(true, model));
        } else {
            return ResponseEntity.badRequest().body(new Message(false));
        }
    }

    @PostMapping("/api/logout")
    public ResponseEntity<Message> logout(HttpSession httpSession) {
        httpSession.removeAttribute(Constants.SESSION_EXTRA_USER);

        return ResponseEntity.ok(new Message(true));
    }

    @PostMapping("/api/user/change")
    public ResponseEntity<Message> changeUser(@RequestBody(required = false) UserModel user) {
        final UserModel userModel = service.changeUser(user);
        return ResponseEntity.ok(new Message<>(true, userModel));
    }

    @GetMapping("/api/user")
    public ResponseEntity<Message> currentUser(HttpSession httpSession) {
        final UserModel user = (UserModel) httpSession.getAttribute(Constants.SESSION_EXTRA_USER);
        if (user == null) {
            throw new NeedAuthorization();
        }

        return ResponseEntity.ok(new Message<>(true, user));
    }
}
