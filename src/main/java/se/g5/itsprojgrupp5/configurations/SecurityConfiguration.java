package se.g5.itsprojgrupp5.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import se.g5.itsprojgrupp5.service.CustomUserDetailsService;


// TODO Add class comment
@Configuration
public class SecurityConfiguration {


    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/").permitAll()
                                .requestMatchers("/**").hasAuthority("ADMIN")
                                .anyRequest()
                                .authenticated())
                .httpBasic(Customizer.withDefaults())
                .formLogin(form -> form
                        .defaultSuccessUrl("/"));
        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
