package se.g5.itsprojgrupp5.configurations;

import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import se.g5.itsprojgrupp5.model.AppUser;
import se.g5.itsprojgrupp5.repository.UserRepository;

@Component
public class AddDefaultUser {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AddDefaultUser(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void generateUsers() {
        AppUser admin = new AppUser();
        admin.setEmail("admin@admin.se");
        admin.setPassword(passwordEncoder.encode("adminpass"));
        admin.setRole("ADMIN");
        userRepository.save(admin);

        AppUser defaultUser = new AppUser();
        defaultUser.setEmail("user@user.se");
        defaultUser.setPassword(passwordEncoder.encode("userpass"));
        defaultUser.setRole("USER");
        userRepository.save(defaultUser);
    }

}
