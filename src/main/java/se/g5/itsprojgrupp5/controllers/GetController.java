package se.g5.itsprojgrupp5.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import se.g5.itsprojgrupp5.dto.UserDTO;

// TODO Add class comment
@Controller("/")
public class GetController {

    @GetMapping("/Welcome")
    public String welcomePage () {
        return "welcomePage";
    }

    @GetMapping("/redirectPage")
    public String exitPage () {
        return "exitPage";
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

}
