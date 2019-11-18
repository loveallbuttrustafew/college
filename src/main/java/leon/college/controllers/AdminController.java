package leon.college.controllers;

import leon.college.models.Group;
import leon.college.models.Role;
import leon.college.models.User;
import leon.college.services.GroupService;
import leon.college.services.UserService;
import leon.college.services.exceptions.GroupAlreadyExists;
import leon.college.services.exceptions.GroupNotFoundException;
import leon.college.services.exceptions.UserAlreadyExistsException;
import leon.college.services.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
        List<User> students = null;
        try {
            students = userService.findByRole(Role.STUDENT);
        } catch (UserNotFoundException e) {
            return "admin/students";
        }

        model.addAttribute("students", students);
        return "admin/students";
    }

    @PostMapping(value = "/admin/students/add", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String addStudent(@RequestParam Map<String, String> args) {
        String username = args.get("username");
        String password = args.get("password");
        String firstName = args.get("firstName");
        String lastName = args.get("lastName");
        Long groupId = Long.valueOf(args.get("groupId"));

        User user = null;
        try {
            user = userService.findByUsername(username);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
            return "redirect:/admin/students";
        }

        Group group = null;
        try {
            group = groupService.findByGroupId(groupId);
        } catch (GroupNotFoundException e) {
            return "redirect:/admin/students";
        }

        user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setGroup(group);
        user.setRole(Role.STUDENT);
        user.setEnabled(true);

        try {
            userService.addUser(user);
        } catch (UserAlreadyExistsException e) {
            return "redirect:/admin/students";
        }

        return "redirect:/admin/students";
    }

    @GetMapping("/admin/teachers")
    public String teachers(Model model) {
        List<User> teachers = null;
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
        User user = new User();
        user.setUsername(args.get("username"));
        user.setPassword(args.get("password"));
        user.setFirstName(args.get("firstName"));
        user.setLastName(args.get("lastName"));
        user.setRole(Role.TEACHER);
        user.setEnabled(true);

        try {
            userService.addUser(user);
        } catch (UserAlreadyExistsException e) {
            return "redirect:/admin/teachers";
        }

        return "redirect:/admin/teachers";
    }

    @GetMapping("/admin/groups")
    public String groups(Model model) {
        List<Group> groups = null;
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
        Group group = new Group();
        group.setTitle(args.get("title"));
        group.setCourse(Byte.parseByte(args.get("course")));
        group.setNumber(Byte.parseByte(args.get("number")));
        group.setCommercial(args.get("commercial") != null);


        try {
            groupService.addGroup(group);
        } catch (GroupAlreadyExists groupAlreadyExists) {
            return "redirect:/admin/groups";
        }

        return "redirect:/admin/groups";
    }

    @GetMapping("/admin/groups/{id}")
    public String groupInfo(@PathVariable String id, Model model) {
        Group group = null;
        try {
            group = groupService.findByGroupId(Long.valueOf(id));
        } catch (GroupNotFoundException e) {
            return "redirect:/admin/groups";
        }

        model.addAttribute("group", group);
        return "admin/groupInfo";
    }

    @GetMapping("/admin/settings")
    public String settings() {
        return "admin/settings";
    }
}
