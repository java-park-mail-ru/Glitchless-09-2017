package ru.glitchless.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.glitchless.models.Message;
import ru.glitchless.models.UserModel;
import ru.glitchless.throwables.InvalidData;
import ru.glitchless.utils.Constants;

import javax.servlet.http.HttpSession;

@RestController
@CrossOrigin(Constants.URL)
public class UserController {
    private UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/api/signup")
    public ResponseEntity signUp(UserModel userModel, HttpSession httpSession) {
        if (userModel == null) {
            return ResponseEntity.badRequest().body(new Message("Empty field: password or login", false));
        }

        try {
            final String session = service.registerUser(userModel);
            httpSession.setAttribute("session", session);
            return ResponseEntity.ok(new Message(session, true));
        } catch (InvalidData e) {
            return ResponseEntity.badRequest().body(new Message(e.getReason(), false));
        }
    }

    @PostMapping("/api/login")
    public ResponseEntity login(UserModel userModel, HttpSession httpSession) {
        if (userModel == null) {
            return ResponseEntity.badRequest().body(new Message("Empty field: password or login", false));
        }

        try {
            final String session = service.authUser(userModel);
            httpSession.setAttribute("session", session);
            return ResponseEntity.ok(new Message(session, true));
        } catch (InvalidData e) {
            return ResponseEntity.badRequest().body(new Message(e.getReason(), false));
        }
    }

    @GetMapping("/api/logout")
    public ResponseEntity logout(HttpSession httpSession) {
        final String session = (String) httpSession.getAttribute("session");
        if (session == null) {
            return ResponseEntity.badRequest().body(new Message("Not have current auth", false));
        }

        service.logout(session);

        return ResponseEntity.ok(new Message(true));
    }

    @PostMapping("/api/user/change")
    public ResponseEntity changeUser(UserModel user) {
        try {
            final UserModel userModel = service.changeUser(user);
            return ResponseEntity.ok(new Message(userModel, true));
        } catch (InvalidData e) {
            return ResponseEntity.badRequest().body(new Message(e.getReason(), false));
        }
    }

    @GetMapping("/api/user")
    public ResponseEntity currentUser(HttpSession httpSession) {
        final String session = (String) httpSession.getAttribute("session");
        if (session == null) {
            return ResponseEntity.badRequest().body(new Message("Not have current auth", false));
        }

        final UserModel currentUser = service.getUserBySession(session);
        if (currentUser == null) {
            return ResponseEntity.badRequest().body(new Message("Invalid session", false));
        }

        return ResponseEntity.ok(new Message(currentUser, true));
    }
}
