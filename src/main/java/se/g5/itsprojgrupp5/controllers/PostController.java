package se.g5.itsprojgrupp5.controllers;

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

    @PostMapping("/registerUser")
    public String createUser (@ModelAttribute("user") UserDTO userDTO, BindingResult result, Model model) {
        if(result.hasErrors()) {
            //TODO Error handling
            System.out.println("THROW ERRRORS");
            return "registerPage";
        }

//        AppUser user = new AppUser(
//                userDTO.getEmail(),
//                userDTO.getPassword(),
//                userDTO.getRole());
//
//        userRepository.save(user);
        return "login";
    }

}
