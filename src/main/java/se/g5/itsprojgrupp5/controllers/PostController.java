package se.g5.itsprojgrupp5.controllers;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import se.g5.itsprojgrupp5.dto.EmailDTO;
import se.g5.itsprojgrupp5.dto.UserDTO;
import se.g5.itsprojgrupp5.model.AppUser;
import se.g5.itsprojgrupp5.repository.UserRepository;

// TODO Add class comment
@Controller
public class PostController {

    //TODO Different injection?
    private static final Logger logger = LoggerFactory.getLogger(PostController.class);

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public PostController(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @PostMapping("/register/user")
    public String createUser (@Valid @ModelAttribute("user") UserDTO userDTO, BindingResult result, Model model) {
        if(result.hasErrors()) {
            logger.debug("Failed to add user because the input has errors");
            return "registerUser";
        }
        userRepository.save(
                new AppUser.AppUserBuilder()
                        .withEmail(userDTO.getEmail())
                        .withPassword(passwordEncoder.encode(userDTO.getPassword()))
                        .withRole(userDTO.getRole())
                        .withName(userDTO.getName())
                        .withSurname(userDTO.getSurname())
                        .withAge(userDTO.getAge())
                        .build()
        );
        model.addAttribute("savedUser", userDTO);
        logger.debug("successfully added user");
        return "registerSuccess";
    }


    @PostMapping("/remove/user")
    public String deleteUser(@ModelAttribute("email") EmailDTO emailDto, Model model) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;

        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
            logger.debug("Attempted to remove user, but login credentials were faulty");
        } else {
            return "search";
        }

        AppUser userToBeDeleted = userRepository.findByEmail(emailDto.getEmail());

        if (userToBeDeleted == null) {
            model.addAttribute("message", "Could not find user with email: " + emailDto.getEmail());
            logger.debug("Could not remove user: user does not exist with email: {}", emailDto.getEmail());
            return "removeUserFailure";
        } else if (username.equals(emailDto.getEmail())) {
            model.addAttribute("message", "Could not remove user: " + emailDto.getEmail() + " as it is the current user.");
            logger.debug("Could not remove user: user is currently logged in with email: {}", emailDto.getEmail());
            return "removeUserFailure";
        } else {
            userRepository.delete(userToBeDeleted);
            logger.debug("removed user with username {}", emailDto.getEmail());
            return "removeUserSuccess";
        }

    }
}