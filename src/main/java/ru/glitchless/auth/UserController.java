package ru.glitchless.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.glitchless.models.Message;
import ru.glitchless.models.UserLocalModel;
import ru.glitchless.models.UserModel;
import ru.glitchless.models.mappers.Mapper;
import ru.glitchless.throwables.InvalidData;
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
    public ResponseEntity<?> signUp(@RequestBody(required = false) UserModel userModel, HttpSession httpSession) {
        if (userModel == null) {
            return ResponseEntity.badRequest().body(new Message("Empty field: password or login", false));
        }

        try {
            final UserLocalModel model = service.registerUser(userModel);
            if (model != null) {
                httpSession.setAttribute(Constants.SESSION_EXTRA_USER, model);
                return ResponseEntity.ok(new Message(mapper.map(model), true));
            } else {
                return ResponseEntity.badRequest().body(new Message(false));
            }
        } catch (InvalidData e) {
            return ResponseEntity.badRequest().body(new Message(e.getMessage(), false));
        }
    }

    @PostMapping("/api/login")
    public ResponseEntity<?> login(@RequestBody(required = false) UserModel userModel, HttpSession httpSession) {
        if (userModel == null) {
            return ResponseEntity.badRequest().body(new Message("Empty field: password or login", false));
        }

        try {
            final UserLocalModel model = service.authUser(userModel);
            if (model != null) {
                httpSession.setAttribute(Constants.SESSION_EXTRA_USER, model);
                return ResponseEntity.ok(new Message(mapper.map(model), true));
            } else {
                return ResponseEntity.badRequest().body(new Message(false));
            }
        } catch (InvalidData e) {
            return ResponseEntity.badRequest().body(new Message(e.getMessage(), false));
        }
    }

    @GetMapping("/api/logout")
    public ResponseEntity<?> logout(HttpSession httpSession) {
        httpSession.removeAttribute(Constants.SESSION_EXTRA_USER);

        return ResponseEntity.ok(new Message(true));
    }

    @PostMapping("/api/user/change")
    public ResponseEntity<?> changeUser(UserModel user) {
        try {
            final UserModel userModel = service.changeUser(user);
            return ResponseEntity.ok(new Message(userModel, true));
        } catch (InvalidData e) {
            return ResponseEntity.badRequest().body(new Message(e.getMessage(), false));
        }
    }

    @GetMapping("/api/user")
    public ResponseEntity<?> currentUser(HttpSession httpSession) {
        final UserLocalModel user = (UserLocalModel) httpSession.getAttribute(Constants.SESSION_EXTRA_USER);
        if (user == null) {
            return ResponseEntity.badRequest().body(new Message("Not have current auth", false));
        }

        final UserModel currentUser = mapper.map(user);
        if (currentUser == null) {
            return ResponseEntity.badRequest().body(new Message("Invalid session", false));
        }

        return ResponseEntity.ok(new Message(currentUser, true));
    }
}
