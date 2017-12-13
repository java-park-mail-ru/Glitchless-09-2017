package ru.glitchless.newserver.view.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.glitchless.newserver.data.model.Message;
import ru.glitchless.newserver.data.model.UserModel;
import ru.glitchless.newserver.interactor.auth.UserInteractor;
import ru.glitchless.newserver.utils.Constants;
import ru.glitchless.newserver.data.throwables.InvalidLoginOrPassword;
import ru.glitchless.newserver.data.throwables.NeedAuthorization;

import javax.servlet.http.HttpSession;

@RestController
public class UserController {
    private final UserInteractor userInteractor;

    public UserController(UserInteractor userInteractor) {
        this.userInteractor = userInteractor;
    }

    @PostMapping("/api/signup")
    public ResponseEntity<Message> signUp(@RequestBody(required = false) UserModel userModel, HttpSession httpSession) {
        if (userModel == null) {
            throw new InvalidLoginOrPassword("Empty field: password or login");
        }

        final UserModel model = userInteractor.registerUser(userModel);

        httpSession.setAttribute(Constants.SESSION_EXTRA_USER, model);
        return ResponseEntity.ok(new Message<>(true, model));
    }

    @PostMapping("/api/login")
    public ResponseEntity<Message> login(@RequestBody(required = false) UserModel userModel, HttpSession httpSession) {
        if (userModel == null) {
            throw new InvalidLoginOrPassword("Empty field: password or login");
        }

        final UserModel model = userInteractor.authUser(userModel);

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
        final UserModel userModel = userInteractor.changeUser(user);
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
