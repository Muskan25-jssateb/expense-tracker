package expense_tracker.controller;

import expense_tracker.entity.User;
import expense_tracker.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import expense_tracker.dto.LoginRequest;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public User registerUser(@Valid @RequestBody User user) {
        return userService.registerUser(user);
    }

    @PostMapping("/login")
    public String loginUser(@Valid @RequestBody LoginRequest loginRequest) {
        return userService.loginUser(loginRequest);
    }
}