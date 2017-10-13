package ru.glitchless.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.glitchless.data.mappers.Mapper;
import ru.glitchless.data.models.Message;
import ru.glitchless.data.models.UserLocalModel;
import ru.glitchless.data.models.UserModel;
import ru.glitchless.data.throwables.InvalidLoginOrPassword;
import ru.glitchless.data.throwables.NeedAuthorization;
import ru.glitchless.repositories.auth.UserService;
import ru.glitchless.utils.Constants;

import javax.servlet.http.HttpSession;

@RestController
public class UserController {
    private final UserService service;
    private final Mapper<UserLocalModel, UserModel> mapper;

    public UserController(UserService service, Mapper<UserLocalModel, UserModel> mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping("/api/signup")
    public ResponseEntity<Message> signUp(@RequestBody(required = false) UserModel userModel, HttpSession httpSession) {
        if (userModel == null) {
            throw new InvalidLoginOrPassword("Empty field: password or login");
        }

        final UserLocalModel model = service.registerUser(userModel);
        if (model != null) {
            httpSession.setAttribute(Constants.SESSION_EXTRA_USER, model);
            return ResponseEntity.ok(new Message<>(true, mapper.map(model)));
        } else {
            return ResponseEntity.badRequest().body(new Message(false));
        }
    }

    @PostMapping("/api/login")
    public ResponseEntity<Message> login(@RequestBody(required = false) UserModel userModel, HttpSession httpSession) {
        if (userModel == null) {
            throw new InvalidLoginOrPassword("Empty field: password or login");
        }

        final UserLocalModel model = service.authUser(userModel);
        if (model != null) {
            httpSession.setAttribute(Constants.SESSION_EXTRA_USER, model);
            return ResponseEntity.ok(new Message<>(true, mapper.map(model)));
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
    public ResponseEntity<Message> changeUser(UserModel user) {
        final UserModel userModel = service.changeUser(user);
        return ResponseEntity.ok(new Message<>(true, userModel));
    }

    @PostMapping("/api/user")
    public ResponseEntity<Message> currentUser(@RequestBody(required = false) HttpSession httpSession) {
        final UserLocalModel user = (UserLocalModel) httpSession.getAttribute(Constants.SESSION_EXTRA_USER);
        if (user == null) {
            throw new NeedAuthorization();
        }

        final UserModel currentUser = mapper.map(user);
        if (currentUser == null) {
            throw new NeedAuthorization();
        }

        return ResponseEntity.ok(new Message<>(true, currentUser));
    }
}
