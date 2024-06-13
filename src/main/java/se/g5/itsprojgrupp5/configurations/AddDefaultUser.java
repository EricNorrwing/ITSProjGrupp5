package se.g5.itsprojgrupp5.configurations;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import se.g5.itsprojgrupp5.controllers.GetController;
import se.g5.itsprojgrupp5.model.AppUser;
import se.g5.itsprojgrupp5.repository.UserRepository;

@Component
public class AddDefaultUser {
    //TODO Different injection?
    private static final Logger logger = LoggerFactory.getLogger(GetController.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AddDefaultUser(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void generateUsers() {
        logger.debug("Adding default admin users");
        userRepository.save(
                new AppUser.AppUserBuilder()
                        .withEmail("admin@admin.se")
                        .withPassword(passwordEncoder.encode("adminpass"))
                        .withRole("ADMIN")
                        .withName("Thomas")
                        .withSurname("Thomasson")
                        .withAge(177)
                        .build()
        );

        userRepository.save(
                new AppUser.AppUserBuilder()
                        .withEmail("user@user.se")
                        .withPassword(passwordEncoder.encode("userpass"))
                        .withRole("USER")
                        .withName("Sara")
                        .withSurname("Sarasson")
                        .withAge(167)
                        .build()
        );
    }

}
