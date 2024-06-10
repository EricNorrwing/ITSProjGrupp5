package se.g5.itsprojgrupp5.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
// TODO Add class comment
@Controller
public class PatchController {

    @PatchMapping("/updateUser")
    public String patchUser () {
        return "patchUser";
    }
}
