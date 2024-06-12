package se.g5.itsprojgrupp5.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

// TODO if you want me to change anything just tell me, dont know if you are done on this but if my logging gets in the way just delete it i can redo it -simon

// TODO Add class comment
@Controller
public class DeleteController {

    private static final Logger logger = LoggerFactory.getLogger(DeleteController.class);

    @DeleteMapping("/removeUser")
    public String deleteUser () {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        logger.debug("User '{}' is deleting their account.", username);
        return "deleteUser";
    }

}
