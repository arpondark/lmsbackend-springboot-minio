package site.shazan.lmsbackend.controller;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.shazan.lmsbackend.model.User;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
@NoArgsConstructor
public class UserController {
    public ResponseEntity<User> getUserById(String userId) {

    }
}
