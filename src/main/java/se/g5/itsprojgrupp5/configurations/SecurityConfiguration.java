package se.g5.itsprojgrupp5.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.SecurityFilterChain;


// TODO Add class comment
@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.ignoringRequestMatchers("/registerUser")) //TODO Remove when testing
                .authorizeHttpRequests(
                        authorizeRequests ->
                                authorizeRequests
                                        .requestMatchers("/register", "/registerUser").permitAll()
                                        .requestMatchers("/admin").hasRole("ADMIN")
                                        .anyRequest().authenticated())


                .formLogin(formLogin ->
                        formLogin.loginPage("/login")
                                .authenticationDetailsSource(authenticationDetailsSource)
                                .defaultSuccessUrl("/", true)
                                .failureUrl("/login?error=true")
                                .permitAll())

                .logout(logout ->
                        logout
                                .logoutUrl("/perform_logout") // Define the logout URL
                                .logoutSuccessUrl("/login?logout") // Redirect after logout
                                .deleteCookies("JSESSIONID") // Delete cookies on logout
                                .invalidateHttpSession(true) // Invalidate session on logout
                );

        return http.build();
    }
}
