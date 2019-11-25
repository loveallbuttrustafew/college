package leon.college.controllers.Admin;

import leon.college.models.Group;
import leon.college.services.GroupService;
import leon.college.services.exceptions.GroupAlreadyExists;
import leon.college.services.exceptions.GroupNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/admin/")
public class AdminGroupsController {
    private static final Logger logger = LoggerFactory.getLogger(AdminGroupsController.class);

    @Autowired
    private GroupService groupService;

    @GetMapping("/groups")
    public String groups(Model model) {
        List<Group> groups;
        try {
            groups = groupService.findAll();
            model.addAttribute("groups", groups);
        } catch (GroupNotFoundException e) {
            logger.warn("Group not found");
        }

        return "admin/groups";
    }

    @PostMapping(value = "/groups/add", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String addGroup(Group group) {
        try {
            groupService.addGroup(group);
        } catch (GroupAlreadyExists groupAlreadyExists) {
            logger.error("Group already exists");
        }

        return "redirect:/admin/groups";
    }

    @GetMapping("/groups/{id}")
    public String groupInfo(@PathVariable String id, Model model) {
        Group group;
        try {
            group = groupService.findByGroupId(Long.valueOf(id));
            model.addAttribute("group", group);
        } catch (GroupNotFoundException e) {
            logger.error("Group doesn't exist");
        }

        return "admin/groupInfo";
    }
}
