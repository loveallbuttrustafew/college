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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class AdminSubjectsController {
    Logger logger = LoggerFactory.getLogger(AdminSubjectsController.class);

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private GroupService groupService;

    @GetMapping("/admin/subjects")
    public String subjects(Model model) {
        List<Subject> subjects = subjectService.findAll();
        model.addAttribute("subjects", subjects);
        return "admin/subjects";
    }

    @GetMapping("/admin/subjects/{id}")
    public String groupInfo(@PathVariable String id, Model model) {
        try {
            Subject subject = subjectService.findById(Long.valueOf(id));
            model.addAttribute("subject", subject);
        } catch (SubjectNotFoundException e) {
            logger.warn("Subject doesn't exist");
        }
        return "admin/subjectInfo";
    }

    @PostMapping(value = "/admin/subjects/add", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String addSubject(@RequestParam Map<String, String> args) {
        try {
            Group group = groupService.findByGroupId(Long.valueOf(args.get("groupId")));
            Subject subject = Subject.builder()
                    .title(args.get("title"))
                    .group(group)
                    .build();
            subjectService.addSubject(subject);
        } catch (GroupNotFoundException e) {
            logger.warn("Group doesn't exist");
        } catch (SubjectAlreadyExists subjectAlreadyExists) {
            logger.warn("Subject already exists");
        }

        return "redirect:/admin/subjects";
    }
}
