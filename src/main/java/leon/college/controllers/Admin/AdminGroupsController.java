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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class AdminGroupsController {
    Logger logger = LoggerFactory.getLogger(AdminGroupsController.class);

    @Autowired
    private GroupService groupService;

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
}
