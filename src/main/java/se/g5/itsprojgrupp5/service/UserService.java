package se.g5.itsprojgrupp5.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import se.g5.itsprojgrupp5.configurations.MaskingUtils;
import se.g5.itsprojgrupp5.controllers.GetController;
import se.g5.itsprojgrupp5.model.AppUser;
import se.g5.itsprojgrupp5.repository.UserRepository;

/*
Layer for interacting with user objects.
 */

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(GetController.class);
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void saveUser (AppUser user) {
        userRepository.save(user);
        logger.debug("saved user with username {}", MaskingUtils.anonymizeEmail(user.getEmail()));
    }

    public void deleteUser (String email) {
        AppUser user = userRepository.findByEmail(email);
        userRepository.delete(user);
        logger.debug("removed user with username {}", MaskingUtils.anonymizeEmail(user.getEmail()));
    }

    public AppUser loadUserByUsername(String email) throws UsernameNotFoundException {
        AppUser user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("No user found with username: " + email);
        }
        return user;
    }
}
