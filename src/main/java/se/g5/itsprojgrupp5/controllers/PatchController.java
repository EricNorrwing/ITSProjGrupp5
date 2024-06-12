package se.g5.itsprojgrupp5.controllers;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



// TODO Add class comment
@Controller
public class PatchController {

    private static final Logger logger = LoggerFactory.getLogger(PatchController.class);

    @PatchMapping("/updateUser")
    public String patchUser () {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        logger.debug("User '{}' is updating their account.", username);
        return "patchUser";
        //todo maybe display in the logs what they changed username to, again if this gets in the way feel free to delete
    }
}
