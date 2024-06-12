
package se.g5.itsprojgrupp5.controllers;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;
import se.g5.itsprojgrupp5.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Paths;

// TODO Add class comment
@Controller()
public class GetController {

    private static final Logger logger = LoggerFactory.getLogger(GetController.class);

    @GetMapping("/register/user")
    public String registerUser(Model model) {
        model.addAttribute("user", new UserDTO());
        logger.debug("Accessing the register user page.");
        return "registerUser";
    }

    @GetMapping("/register/success")
    public String registerSuccess(@ModelAttribute("user") UserDTO userDTO, Model model) {
        model.addAttribute("user", new UserDTO());
        return "registerUser";
    }

    @GetMapping("/remove/user")
    public String removeUserPage() {
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

    @GetMapping("/logout-success")
    public String logoutSuccess(Model model) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        model.addAttribute("message", "Du är utloggad!");
        return "logout";
    }
}



