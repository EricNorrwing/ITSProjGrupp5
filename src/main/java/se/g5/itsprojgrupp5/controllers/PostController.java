package se.g5.itsprojgrupp5.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
// TODO Add class comment
@Controller
public class PostController {

    @PostMapping("/registerUser")
    public String createUser () {
        return "createUser";
    }

}
