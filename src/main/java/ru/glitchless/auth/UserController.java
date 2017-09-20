package ru.glitchless.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.glitchless.models.Message;
import ru.glitchless.models.UserLocalModel;
import ru.glitchless.models.UserModel;

import javax.servlet.http.HttpSession;

@RestController
@CrossOrigin
public class UserController {
    UserService service;
    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    public UserController(UserService service){
        this.service = service;
    }

    @PostMapping("/signup")
    public ResponseEntity signUp(@RequestBody(required = false) UserModel userModel, HttpSession httpSession) {
        if(userModel == null){
            return ResponseEntity.badRequest().body(new Message("Empty field: password or login", false));
        }


        return ResponseEntity.ok(new Message(userModel, true));
    }

    @GetMapping("/")
    public ResponseEntity index(HttpSession httpSession) {
        return ResponseEntity.ok(new Message("OK!"));
    }
}
