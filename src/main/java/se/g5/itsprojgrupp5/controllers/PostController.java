package se.g5.itsprojgrupp5.controllers;

import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import se.g5.itsprojgrupp5.dto.UserDTO;
import se.g5.itsprojgrupp5.model.AppUser;
import se.g5.itsprojgrupp5.repository.UserRepository;

// TODO Add class comment
@Controller
public class PostController {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public PostController(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @PostMapping("/register/user")
    public String createUser (@Valid @ModelAttribute("user") UserDTO userDTO, BindingResult result, Model model) {
        if(result.hasErrors()) {
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
        return "registerSuccess";
    }

}
