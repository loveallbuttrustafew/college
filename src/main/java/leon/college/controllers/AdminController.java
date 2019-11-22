package leon.college.controllers;

import leon.college.models.Group;
import leon.college.models.Role;
import leon.college.models.User;
import leon.college.services.GroupService;
import leon.college.services.UserService;
import leon.college.services.exceptions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class AdminController {
    Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private GroupService groupService;

    @GetMapping("/admin")
    public String admin() {
        return "redirect:/admin/groups";
    }

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

    @GetMapping("/admin/teachers")
    public String teachers(Model model) {
        List<User> teachers;
        try {
            teachers = userService.findByRole(Role.TEACHER);
        } catch (UserNotFoundException e) {
            return "admin/teachers";
        }

        model.addAttribute("teachers", teachers);
        return "admin/teachers";
    }

    @PostMapping(value = "/admin/teachers/add", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
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
            logger.warn("Username already taken");
        }

        return "redirect:/admin/teachers";
    }

    @GetMapping("/admin/groups")
    public String groups(Model model) {
        List<Group> groups;
        try {
            groups = groupService.findAll();
        } catch (GroupNotFoundException e) {
            return "admin/groups";
        }

        model.addAttribute("groups", groups);
        return "admin/groups";
    }

    @PostMapping(value = "/admin/groups/add", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String addGroup(@RequestParam Map<String, String> args) {
        Group group = Group.builder()
                .title(args.get("title"))
                .course(Byte.parseByte(args.get("course")))
                .number(Byte.parseByte(args.get("number")))
                .commercial(args.get("commercial") != null)
                .build();

        try {
            groupService.addGroup(group);
        } catch (GroupAlreadyExists groupAlreadyExists) {
            logger.warn("Group already exists");
        }

        return "redirect:/admin/groups";
    }

    @GetMapping("/admin/groups/{id}")
    public String groupInfo(@PathVariable String id, Model model) {
        Group group;
        try {
            group = groupService.findByGroupId(Long.valueOf(id));
            model.addAttribute("group", group);
        } catch (GroupNotFoundException e) {
            logger.warn("Group doesn't exist");
        }

        return "admin/groupInfo";
    }

    @GetMapping("/admin/settings")
    public String settings() {
        return "admin/settings";
    }

    @PostMapping(value = "/admin/settings/changepassword", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String changePassword(@RequestParam Map<String, String> args, Authentication authentication) {
        try {
            userService.changePassword(authentication.getName(), args.get("oldPassword"), args.get("newPassword"));
        } catch (UserNotFoundException e) {
            logger.warn("User dosn't exist");
        } catch (PasswordsDontMatchException e) {
            logger.warn("Passwords don't match each other");
        }

        return "redirect:/admin/settings";
    }
}
