package se.g5.itsprojgrupp5.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DeleteController {

    @DeleteMapping("/removeUser")
    public String deleteUser () {
        return "deleteUser";
    }

}
