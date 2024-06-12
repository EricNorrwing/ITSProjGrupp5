package se.g5.itsprojgrupp5.controllers;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import se.g5.itsprojgrupp5.dto.UserDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Paths;

// TODO Add class comment
@Controller()
public class GetController {

    private static final Logger logger = LoggerFactory.getLogger(GetController.class);

    /**
     * static {
        String workingDir = Paths.get("").toAbsolutePath().toString();
        logger.debug("Current working directory: {}", workingDir);
    }
     **/

    @GetMapping("/welcome")
    public String welcomePage () {
        logger.debug("Accessing the welcome page.");
        return "welcomePage";
    }

    @GetMapping("/registerUser")
    public String registerUser (Model model) {
        logger.debug("Accessing the register user page.");
        model.addAttribute("user", new UserDTO());
        return "registerUser";
    }

    @GetMapping("/removeUser")
    public String removeUserPage () {
        logger.debug("Accessing the remove user page.");
        return "removeUserPage";
    }

    @GetMapping("/")
    public String home(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;

        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
            logger.debug("Authenticated user found: {}", username);
        } else {
            username = principal.toString();
            logger.debug("Authenticated principal found: {}", username);
        }

        model.addAttribute("message", "Hej " + username + ", välkommen!");
        return "home";
    }
}


