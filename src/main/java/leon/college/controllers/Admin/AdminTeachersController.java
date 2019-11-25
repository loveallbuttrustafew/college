package leon.college.controllers.Admin;

import leon.college.models.Role;
import leon.college.models.User;
import leon.college.services.UserService;
import leon.college.services.exceptions.UserAlreadyExistsException;
import leon.college.services.exceptions.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/admin")
public class AdminTeachersController {
    private static final Logger logger = LoggerFactory.getLogger(AdminTeachersController.class);

    @Autowired
    private UserService userService;

    @GetMapping("/teachers")
    public String teachers(Model model) {
        List<User> teachers;
        try {
            teachers = userService.findByRole(Role.TEACHER);
            model.addAttribute("teachers", teachers);
        } catch (UserNotFoundException e) {
            logger.error("User doesn't found");
        }

        return "admin/teachers";
    }

    @PostMapping(value = "/teachers/add", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String addTeacher(@RequestParam Map<String, String> args) {
        User user = User.builder()
                .username(args.get("username"))
                .password(args.get("password"))
                .firstName(args.get("firstName"))
                .lastName(args.get("lastName"))
                .role(Role.TEACHER)
                .enabled(true)
                .build();

        try {
            userService.addUser(user);
        } catch (UserAlreadyExistsException e) {
            logger.error("Username already taken");
        }

        return "redirect:/admin/teachers";
    }

}
