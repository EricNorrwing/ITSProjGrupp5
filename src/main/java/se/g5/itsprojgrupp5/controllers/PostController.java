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
import se.g5.itsprojgrupp5.dto.EmailDTO;
import se.g5.itsprojgrupp5.dto.UpdateUserDTO;
import se.g5.itsprojgrupp5.dto.UserDTO;
import se.g5.itsprojgrupp5.model.AppUser;
import se.g5.itsprojgrupp5.repository.UserRepository;
import se.g5.itsprojgrupp5.service.CustomUserDetailsService;
import se.g5.itsprojgrupp5.configurations.MaskingUtils;

// TODO Add class comment
@Controller
public class PostController {

    //TODO Different injection?
    private static final Logger logger = LoggerFactory.getLogger(PostController.class);

    private final PasswordEncoder passwordEncoder;
    private final CustomUserDetailsService customUserDetailsService;

    public PostController(PasswordEncoder passwordEncoder, UserRepository userRepository, CustomUserDetailsService customUserDetailsService) {
        this.passwordEncoder = passwordEncoder;

        this.customUserDetailsService = customUserDetailsService;
    }

    //TODO CLEAN DATA WITH HTMLUTILS
    @PostMapping("/register/user")
    public String createUser (@Valid @ModelAttribute("user") UserDTO userDTO, BindingResult result, Model model) {
        if(result.hasErrors()) {
            logger.debug("Failed to add user because the input has errors");
            return "registerUser";
        }
        customUserDetailsService.saveUser(new AppUser.AppUserBuilder()
                .withEmail(userDTO.getEmail())
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
    public String deleteUser(@ModelAttribute("email") EmailDTO emailDto, Model model) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;

        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            logger.debug("Attempted to remove user, but login credentials were faulty");
            return "search";
        }

        try {
            if (username.equals(emailDto.getEmail())) {
                model.addAttribute("message", "Could not remove user: " + emailDto.getEmail() + " as it is the current user.");
                logger.debug("Could not remove user: user is currently logged in with email: {}", MaskingUtils.anonymizeEmail(emailDto.getEmail()));
                return "search";
            } else {
                customUserDetailsService.deleteUser(emailDto.getEmail());
                logger.debug("Removed user with email {}", MaskingUtils.anonymizeEmail(emailDto.getEmail()));
                model.addAttribute("message", "User removed successfully: " + emailDto.getEmail());
                return "removeUserSuccess";
            }
        } catch (UsernameNotFoundException ex) {
            model.addAttribute("message", "Could not find user with email: " + emailDto.getEmail());
            logger.debug("Could not remove user: user does not exist with email: {}", MaskingUtils.anonymizeEmail(emailDto.getEmail()));
            return "search";
        } catch (Exception ex) {
            model.addAttribute("message", "An error occurred while trying to remove the user.");
            logger.error("An unexpected error occurred while removing user: {}", MaskingUtils.anonymizeEmail(emailDto.getEmail()), ex);
            return "search";
        }

    }

    @PostMapping("/update/user")
    public String updateUser(@Valid @ModelAttribute("updateUserDTO") UpdateUserDTO userDTO, BindingResult result, Model model) {
        if (result.hasErrors()) {
            logger.debug("Failed to update user because the input has errors");
            return "updateUser";
        }

        try {
            AppUser user = customUserDetailsService.loadUserByUsername(userDTO.getEmail());
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            customUserDetailsService.saveUser(user);

            model.addAttribute("message", "Successfully updated user " + userDTO.getEmail() + " with password " + userDTO.getPassword());
            logger.debug("Successfully updated user {} with new password", MaskingUtils.anonymizeEmail(user.getEmail()));
            return "updateSuccess";
        } catch (UsernameNotFoundException ex) {
            model.addAttribute("message", "User not found: " + userDTO.getEmail());
            logger.debug("User not found: {}", MaskingUtils.anonymizeEmail(userDTO.getEmail()));
            return "updateUser";
        } catch (Exception ex) {
            model.addAttribute("message", "An error occurred while updating the user.");
            logger.error("An unexpected error occurred while updating user: {}", MaskingUtils.anonymizeEmail(userDTO.getEmail()), ex);
            return "updateUser";
        }
    }
}