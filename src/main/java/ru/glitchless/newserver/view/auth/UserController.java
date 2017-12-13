package ru.glitchless.newserver.view.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.glitchless.newserver.data.model.Message;
import ru.glitchless.newserver.data.model.UserModel;
import ru.glitchless.newserver.interractor.auth.UserInterractor;
import ru.glitchless.server.data.throwables.InvalidLoginOrPassword;
import ru.glitchless.server.data.throwables.NeedAuthorization;
import ru.glitchless.newserver.utils.Constants;

import javax.servlet.http.HttpSession;

@RestController
public class UserController {
    private final UserInterractor userInterractor;

    public UserController(UserInterractor userInterractor) {
        this.userInterractor = userInterractor;
    }

    @PostMapping("/api/signup")
    public ResponseEntity<Message> signUp(@RequestBody(required = false) UserModel userModel, HttpSession httpSession) {
        if (userModel == null) {
            throw new InvalidLoginOrPassword("Empty field: password or login");
        }

        final UserModel model = userInterractor.registerUser(userModel);

        httpSession.setAttribute(Constants.SESSION_EXTRA_USER, model);
        return ResponseEntity.ok(new Message<>(true, model));
    }

    @PostMapping("/api/login")
    public ResponseEntity<Message> login(@RequestBody(required = false) UserModel userModel, HttpSession httpSession) {
        if (userModel == null) {
            throw new InvalidLoginOrPassword("Empty field: password or login");
        }

        final UserModel model = userInterractor.authUser(userModel);

        httpSession.setAttribute(Constants.SESSION_EXTRA_USER, model);
        return ResponseEntity.ok(new Message<>(true, model));
    }

    @PostMapping("/api/logout")
    public ResponseEntity<Message> logout(HttpSession httpSession) {
        httpSession.removeAttribute(Constants.SESSION_EXTRA_USER);

        return ResponseEntity.ok(new Message(true));
    }

    @PostMapping("/api/user/change")
    public ResponseEntity<Message> changeUser(@RequestBody(required = false) UserModel user) {
        final UserModel userModel = userInterractor.changeUser(user);
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
