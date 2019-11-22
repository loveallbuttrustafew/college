package leon.college.controllers.Admin;

import leon.college.models.Group;
import leon.college.models.Role;
import leon.college.models.User;
import leon.college.services.GroupService;
import leon.college.services.UserService;
import leon.college.services.exceptions.GroupNotFoundException;
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
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;


@Controller
 class AdminStudentsController {
    Logger logger = LoggerFactory.getLogger(AdminStudentsController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private GroupService groupService;

    @GetMapping("/admin/students")
    public String students(Model model) {
        List<User> students;
        try {
            students = userService.findByRole(Role.STUDENT);
            model.addAttribute("students", students);
        } catch (UserNotFoundException e) {
            logger.error("Students not found");
        }

        return "admin/students";
    }

    @PostMapping(value = "/admin/students/add", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String addStudent(@RequestParam Map<String, String> args) {
        Group group;
        try {
            group = groupService.findByGroupId(Long.valueOf(args.get("groupId")));
            User user = User.builder()
                    .username(args.get("username"))
                    .password(args.get("password"))
                    .firstName(args.get("firstName"))
                    .lastName(args.get("lastName"))
                    .role(Role.STUDENT)
                    .group(group)
                    .enabled(true)
                    .build();
            userService.addUser(user);
        } catch (GroupNotFoundException e) {
            logger.warn("Group doesn't exists");
        } catch (UserAlreadyExistsException e) {
            logger.warn("Username already taken");
        }

        return "redirect:/admin/students";
    }
}
