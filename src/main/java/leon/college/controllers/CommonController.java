package leon.college.controllers;

import leon.college.models.Role;
import leon.college.models.User;
import leon.college.services.UserService;
import leon.college.services.exceptions.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CommonController {
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/init")
    public String init() {
        User user = User.builder()
                .username("admin")
                .password("admin")
                .role(Role.ADMIN)
                .enabled(true)
                .build();

        try {
            userService.addUser(user);
        } catch (UserAlreadyExistsException e) {
            return "redirect:/";
        }
        return "redirect:/";
    }
}
