
package se.g5.itsprojgrupp5.controllers;

import jakarta.validation.Valid;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import se.g5.itsprojgrupp5.configurations.MaskingUtils;
import se.g5.itsprojgrupp5.dto.EmailDTO;
import se.g5.itsprojgrupp5.dto.UpdateUserDTO;
import se.g5.itsprojgrupp5.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.g5.itsprojgrupp5.service.UserService;

// TODO Add class comment
@Controller
public class GetController {

    private static final Logger logger = LoggerFactory.getLogger(GetController.class);

    private final UserService userService;

    public GetController(UserService userService) {
        this.userService = userService;
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
        logger.debug("Registration successful for user: {}", MaskingUtils.anonymizeEmail(userDTO.getEmail()));
        return "registerUser";
    }

    @GetMapping("/")
    public String home(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
            logger.debug("Authenticated user found: {}", MaskingUtils.anonymizeEmail(username));
        } else {
            username = principal.toString();
            logger.debug("Authenticated principal found: {}", MaskingUtils.anonymizeEmail(username));
        }

        model.addAttribute("message", "Hej " + MaskingUtils.anonymizeEmail(username) + ", välkommen!");
        return "home";
    }

    @GetMapping("/update/user")
    public String updateUserForm(@ModelAttribute("emailDTO") EmailDTO emailDTO, Model model) {

        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();

        if (currentUser.equals(emailDTO.getEmail())) {
            model.addAttribute("message", "Could not remove the user as it is the current user.");
            logger.warn("Attempted to delete own account with email: {}", MaskingUtils.anonymizeEmail(emailDTO.getEmail()));
            model.addAttribute("email", emailDTO);
            return "search";
        }

        if(userService.exists(emailDTO.getEmail())) {

            UpdateUserDTO updateUserDTO = new UpdateUserDTO();
            updateUserDTO.setEmail(emailDTO.getEmail());
            model.addAttribute("updateUserDTO", updateUserDTO);
            logger.debug("Accessing the updateUser form with email: {}", MaskingUtils.anonymizeEmail(emailDTO.getEmail()));
            return "updateUser";
        } else {
            model.addAttribute("email", new EmailDTO());
            model.addAttribute("message", "Error, could not find user by name: " + emailDTO.getEmail());
            logger.warn("Could not find user by this email: {}", MaskingUtils.anonymizeEmail(emailDTO.getEmail()));
            return "search";
        }
    }

    @GetMapping("/search")
    public String Search(Model model) {
        model.addAttribute("email", new EmailDTO());
        logger.debug("Accessing the search page.");
        return "search";
    }

    @GetMapping("/admin/page")
    public String adminpage () {
        logger.debug("Accessing the admin page.");
        return "adminPage";
    }
}



