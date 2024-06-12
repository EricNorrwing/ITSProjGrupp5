package se.g5.itsprojgrupp5.controllers;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import se.g5.itsprojgrupp5.dto.UserDTO;

// TODO Add class comment
@Controller()
public class GetController {

    @GetMapping("/welcome")
    public String welcomePage () {
        return "welcomePage";
    }

    @GetMapping("/registerUser")
    public String registerUser (Model model) {
        model.addAttribute("user", new UserDTO());
        return "registerUser";
    }

    @GetMapping("/removeUser")
    public String removeUserPage () {
        return "removeUserPage";
    }

    @GetMapping("/")
    public String home(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
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



