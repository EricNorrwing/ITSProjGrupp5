package se.g5.itsprojgrupp5.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
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
    public String registerUser () {
        return "registerUser";
    }

    @GetMapping("/removeUser")
    public String removeUserPage () {
        return "removeUserPage";
    }

}
