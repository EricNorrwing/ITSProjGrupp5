package se.g5.itsprojgrupp5.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import se.g5.itsprojgrupp5.repository.UserRepository;
import se.g5.itsprojgrupp5.service.UserDetailService;


// TODO Add class comment
@Configuration
public class SecurityConfiguration {

    private final UserDetailService userDetailService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public SecurityConfiguration(UserDetailService userDetailService, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.userDetailService = userDetailService;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.ignoringRequestMatchers("/registerUser")) //TODO Remove when testing
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests.anyRequest().authenticated())
                .formLogin(formLogin ->
                        formLogin
                                .authenticationDetailsSource()

        return http.build();
    }



    @Bean
    public UserDetailService userDetailsService() {
        var userDetailsService = new UserDetailService(userRepository);
        return userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
