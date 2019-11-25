package leon.college.controllers.Admin;

import leon.college.models.Group;
import leon.college.models.Subject;
import leon.college.services.GroupService;
import leon.college.services.SubjectService;
import leon.college.services.exceptions.GroupNotFoundException;
import leon.college.services.exceptions.SubjectAlreadyExists;
import leon.college.services.exceptions.SubjectNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/admin")
public class AdminSubjectsController {
    private static final Logger logger = LoggerFactory.getLogger(AdminSubjectsController.class);

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private GroupService groupService;

    @GetMapping("/subjects")
    public String subjects(Model model) {
        List<Subject> subjects = subjectService.findAll();
        model.addAttribute("subjects", subjects);
        return "admin/subjects";
    }

    @GetMapping("/subjects/{id}")
    public String groupInfo(@PathVariable String id, Model model) {
        try {
            Subject subject = subjectService.findById(Long.valueOf(id));
            model.addAttribute("subject", subject);
        } catch (SubjectNotFoundException e) {
            logger.error("Subject doesn't exist");
        }
        return "admin/subjectInfo";
    }

    @PostMapping(value = "/subjects/add", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String addSubject(@RequestParam Map<String, String> args) {
        try {
            Group group = groupService.findByGroupId(Long.valueOf(args.get("groupId")));
            Subject subject = Subject.builder()
                    .title(args.get("title"))
                    .group(group)
                    .build();
            subjectService.addSubject(subject);
        } catch (GroupNotFoundException e) {
            logger.error("Group doesn't exist");
        } catch (SubjectAlreadyExists subjectAlreadyExists) {
            logger.error("Subject already exists");
        }

        return "redirect:/admin/subjects";
    }
}
