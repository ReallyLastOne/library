package reallylastone.librarymanagementsystem.controllers;

import org.springframework.web.bind.annotation.RestController;
import reallylastone.librarymanagementsystem.services.UserService;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
}
