package se.g5.itsprojgrupp5.controllers;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.util.HtmlUtils;
import se.g5.itsprojgrupp5.dto.EmailDTO;
import se.g5.itsprojgrupp5.dto.UpdateUserDTO;
import se.g5.itsprojgrupp5.dto.UserDTO;
import se.g5.itsprojgrupp5.model.AppUser;
import se.g5.itsprojgrupp5.repository.UserRepository;
import se.g5.itsprojgrupp5.service.CustomUserDetailsService;
import se.g5.itsprojgrupp5.configurations.MaskingUtils;
import se.g5.itsprojgrupp5.service.UserService;

// TODO Add class comment
@Controller
public class PostController {

    //TODO Different injection?
    private static final Logger logger = LoggerFactory.getLogger(PostController.class);
    private static HtmlUtils htmlUtils;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;


    public PostController(PasswordEncoder passwordEncoder, UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    //TODO CLEAN DATA WITH HTMLUTILS
    @PostMapping("/register/user")
    public String createUser (@Valid @ModelAttribute("user") UserDTO userDTO, BindingResult result, Model model) {
        if(result.hasErrors()) {
            logger.warn("Failed to add user because the input has errors");
            return "registerUser";
        }


        userService.saveUser(new AppUser.AppUserBuilder()
                .withEmail(HtmlUtils.htmlEscape(userDTO.getEmail()))
                .withPassword(passwordEncoder.encode(userDTO.getPassword()))
                .withRole(userDTO.getRole())
                .withName(userDTO.getName())
                .withSurname(userDTO.getSurname())
                .withAge(userDTO.getAge())
                .build());

        model.addAttribute("savedUser", userDTO);
        logger.debug("Successfully added user {}", MaskingUtils.anonymizeEmail(userDTO.getEmail()));
        return "registerSuccess";
    }


    //TODO CLEAN DATA WITH HTMLUTILS
    @PostMapping("/remove/user")
    public String deleteUser(@ModelAttribute("email") @Valid EmailDTO emailDto, BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "search"; // or appropriate error handling logic
        }



        try {
            userService.deleteUser(HtmlUtils.htmlEscape(emailDto.getEmail()));
            logger.debug("Removed user with email {}", MaskingUtils.anonymizeEmail(emailDto.getEmail()));
            model.addAttribute("message", "User removed successfully: " + emailDto.getEmail());
            return "removeUserSuccess";
        } catch (UsernameNotFoundException ex) {
            model.addAttribute("message", "Could not find user with email: " + emailDto.getEmail());
            logger.warn("Could not remove user: user does not exist with email: {}", MaskingUtils.anonymizeEmail(emailDto.getEmail()));
            return "search";
        } catch (Exception ex) {
            model.addAttribute("message", "User does not exist.");
            logger.warn("An unexpected error occurred while removing user: {}", MaskingUtils.anonymizeEmail(emailDto.getEmail()));
            return "search";
        }
    }

    //TODO HTMLUTILS
    @PostMapping("/update/user")
    public String updateUser(@Valid @ModelAttribute("updateUserDTO") UpdateUserDTO userDTO, BindingResult result, Model model) {
        if (result.hasErrors()) {
            logger.warn("Failed to update user because the input has errors");
            return "updateUser";
        }

        try {
            AppUser user = userService.loadUserByUsername(userDTO.getEmail());
            user.setPassword(passwordEncoder.encode(HtmlUtils.htmlEscape(userDTO.getPassword())));
            userService.saveUser(user);

            model.addAttribute("message", "Successfully updated user " + userDTO.getEmail() + " with password " + userDTO.getPassword());
            logger.debug("Successfully updated user {} with new password", MaskingUtils.anonymizeEmail(user.getEmail()));
            return "updateSuccess";
        } catch (UsernameNotFoundException ex) {
            model.addAttribute("message", "User not found: " + userDTO.getEmail());
            logger.warn("User not found: {}", MaskingUtils.anonymizeEmail(userDTO.getEmail()));
            return "updateUser";
        } catch (Exception ex) {
            model.addAttribute("message", "An error occurred while updating the user.");
            logger.warn("An unexpected error occurred while updating user: {}", MaskingUtils.anonymizeEmail(userDTO.getEmail()), ex);
            return "updateUser";
        }
    }
}