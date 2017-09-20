package ru.glitchless.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.glitchless.models.UserModel;

@RestController
@CrossOrigin
public class UserController {

    @PostMapping(path = "/signup")
    public ResponseEntity signUp(@RequestBody(required = false) UserModel profile) {
        if(profile == null){
            return ResponseEntity.badRequest().body("Error!");
        }
        return ResponseEntity.ok("OK!");
    }
}
