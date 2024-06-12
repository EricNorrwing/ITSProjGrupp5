package se.g5.itsprojgrupp5.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import se.g5.itsprojgrupp5.service.CustomUserDetailsService;


// TODO Add class comment
@Configuration
public class SecurityConfiguration {

    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfiguration(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.ignoringRequestMatchers("/register/**")) //TODO Remove when testing
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/register/**").permitAll()
                                .requestMatchers("/").hasRole("ADMIN")
                        .anyRequest()
                        .authenticated())
                .authenticationProvider(customAuthenticationProvider())
                .httpBasic(Customizer.withDefaults())
                .formLogin(form -> form
                        //.loginPage("/login") //TODO need custom login page?
                        .defaultSuccessUrl("/")
                        .failureUrl("/error"));
        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider customAuthenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(customUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
