package leon.college.controllers.Admin;

import leon.college.services.UserService;
import leon.college.services.exceptions.PasswordsDontMatchException;
import leon.college.services.exceptions.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequestMapping(value = "/admin")
public class AdminSettingsController {
    private static final Logger logger = LoggerFactory.getLogger(AdminSettingsController.class);

    @Autowired
    private UserService userService;

    @GetMapping("/settings")
    public String settings() {
        return "admin/settings";
    }

    @PostMapping(value = "/settings/changepassword", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String changePassword(@RequestParam Map<String, String> args, Authentication authentication) {
        try {
            userService.changePassword(authentication.getName(), args.get("oldPassword"), args.get("newPassword"));
        } catch (UserNotFoundException e) {
            logger.error("User doesn't exist");
        } catch (PasswordsDontMatchException e) {
            logger.error("Passwords don't match each other");
        }

        return "redirect:/admin/settings";
    }
}
