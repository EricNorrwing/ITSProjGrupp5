package se.g5.itsprojgrupp5.controllers;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.util.HtmlUtils;
import se.g5.itsprojgrupp5.configurations.MaskingUtils;
import se.g5.itsprojgrupp5.dto.EmailDTO;
import se.g5.itsprojgrupp5.dto.UpdateUserDTO;
import se.g5.itsprojgrupp5.dto.UserDTO;
import se.g5.itsprojgrupp5.model.AppUser;
import se.g5.itsprojgrupp5.service.UserService;

/*
Post endpoints.
 */
@Controller
public class PostController {

    private static final Logger logger = LoggerFactory.getLogger(PostController.class);
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;


    public PostController(PasswordEncoder passwordEncoder, UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }


    @PostMapping("/register/user")
    public String createUser (@Valid @ModelAttribute("user") UserDTO userDTO, BindingResult result, Model model) {
        if(result.hasErrors()) {
            logger.warn("Failed to add user because the input has errors");
            return "registerUser";
        }
        userService.saveUser(new AppUser.AppUserBuilder()
                .withEmail(HtmlUtils.htmlEscape(userDTO.getEmail()))
                .withPassword(HtmlUtils.htmlEscape(passwordEncoder.encode(userDTO.getPassword())))
                .withRole(userDTO.getRole())
                .withName(userDTO.getName())
                .withSurname(userDTO.getSurname())
                .withAge(userDTO.getAge())
                .build());

        model.addAttribute("savedUser", userDTO);
        logger.debug("Successfully added user {}", MaskingUtils.anonymizeEmail(userDTO.getEmail()));
        return "registerSuccess";
    }


    @PostMapping("/remove/user")
    public String deleteUser(@Valid @ModelAttribute("email") EmailDTO emailDto, BindingResult result, Model model) {

        if (result.hasErrors()) {
            logger.warn("Failed to remove user because the input has errors");
            return "search";
        }

        try {
            String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();

            if (currentUser.equals(emailDto.getEmail())) {
                model.addAttribute("message", "Could not remove the user as it is the current user.");
                logger.warn("Attempted to delete own account with email: {}", MaskingUtils.anonymizeEmail(emailDto.getEmail()));
                return "search";
            }

            AppUser user = userService.loadUserByUsername(emailDto.getEmail());
            userService.deleteUser(user.getEmail());
            logger.debug("Removed user with email {}", MaskingUtils.anonymizeEmail(emailDto.getEmail()));
            model.addAttribute("message", "User removed successfully: " + emailDto.getEmail());
            return "removeUserSuccess";

        } catch (UsernameNotFoundException ex) {
            model.addAttribute("message", "Could not find user with email: " + emailDto.getEmail());
            logger.warn("Could not remove user: user does not exist with email: {}", MaskingUtils.anonymizeEmail(emailDto.getEmail()));
            return "search";
        }
    }


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

                model.addAttribute("message", "Successfully updated user " + MaskingUtils.anonymizeEmail(userDTO.getEmail()) + " with password " + userDTO.getPassword());
                logger.debug("Successfully updated user {} with new password", MaskingUtils.anonymizeEmail(user.getEmail()));
                return "updateSuccess";
        } catch (UsernameNotFoundException ex) {
            model.addAttribute("message", "User not found: " + userDTO.getEmail());
            logger.warn("User not found: {}", MaskingUtils.anonymizeEmail(userDTO.getEmail()));
            return "updateUser";
        }
    }
}