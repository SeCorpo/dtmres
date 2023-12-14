package nl.hu.adsd.dtmreserveringen.config;

import nl.hu.adsd.dtmreserveringen.services.AccountService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AccountService accountService;

    public SecurityConfig(AccountService accountService) {
        this.accountService = accountService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/login").permitAll()
                        .anyRequest().permitAll() //.authenticated()
                );
        http
                .formLogin(form -> form
                        .loginPage("/login")
                        .permitAll()
                        .loginProcessingUrl("/api/login/login")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout")
                );

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return accountService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}