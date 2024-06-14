
package se.g5.itsprojgrupp5.controllers;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import se.g5.itsprojgrupp5.dto.EmailDTO;
import se.g5.itsprojgrupp5.dto.UpdateUserDTO;
import se.g5.itsprojgrupp5.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.g5.itsprojgrupp5.model.AppUser;
import se.g5.itsprojgrupp5.repository.UserRepository;
import se.g5.itsprojgrupp5.service.CustomUserDetailsService;

// TODO Add class comment
@Controller
public class GetController {

    //TODO Different injection?
    private static final Logger logger = LoggerFactory.getLogger(GetController.class);

    private final CustomUserDetailsService customUserDetailsService;

    public GetController(PasswordEncoder passwordEncoder, UserRepository userRepository, CustomUserDetailsService customUserDetailsService) {

        this.customUserDetailsService = customUserDetailsService;
    }

    @GetMapping("/register/user")
    public String registerUser(Model model) {
        model.addAttribute("user", new UserDTO());
        logger.debug("Accessing the register user page.");
        return "registerUser";
    }

    @GetMapping("/register/success")
    public String registerSuccess(@ModelAttribute("user") UserDTO userDTO, Model model) {
        model.addAttribute("user", new UserDTO());
        logger.debug("Registration successful for user: {}", userDTO.getEmail()); //?????????????????????? MAYBE

        return "registerUser";
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

    @GetMapping("/update/user")
    public String updateUserForm(@ModelAttribute("emailDTO") EmailDTO emailDTO, Model model) {
        if(customUserDetailsService.exists(emailDTO.getEmail())) {
            UpdateUserDTO updateUserDTO = new UpdateUserDTO();
            updateUserDTO.setEmail(emailDTO.getEmail());
            model.addAttribute("updateUserDTO", updateUserDTO);
            logger.debug("Accessing the updateUser form with email: {}", emailDTO.getEmail());
            return "updateUser";
        } else {
            model.addAttribute("email", new EmailDTO());
            model.addAttribute("message", "Error, could not find user by name: " + emailDTO.getEmail());
            logger.debug("Could not find user by this username: {}", emailDTO.getEmail());
            return "search";
        }


    }

    @GetMapping("/search")
    public String Search(Model model) {
        model.addAttribute("email", new EmailDTO());
        logger.debug("Accessing the search page.");
        return "search";
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
        logger.debug("User logged out: {}", username);
        model.addAttribute("message", "Du är utloggad!");
        return "logout";
    }

    @GetMapping("/admin/page")
    public String adminpage () {
        logger.debug("Accessing the admin page.");
        return "adminPage";
    }
}



