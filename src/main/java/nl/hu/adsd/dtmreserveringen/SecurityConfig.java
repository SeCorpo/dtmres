package nl.hu.adsd.dtmreserveringen;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .formLogin(form -> form
                        .loginPage("/login").permitAll()
                        .defaultSuccessUrl("/").permitAll()
//                        .failureUrl("/register").permitAll() //form login handles failure
                )

                .sessionManagement(session -> session
                        .invalidSessionUrl("/")
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                        .sessionFixation(SessionManagementConfigurer.SessionFixationConfigurer::newSession) //find difference with migrateSession
                        .sessionFixation().migrateSession()
                        .maximumSessions(1) //Disables a user from login in multiple times in different tabs, replaces with the newest login
//                        .maxSessionsPreventsLogin(true) //Prevent further login attempts if the maximum sessions limit is reached
                        )

                // the security matcher makes sure only the pattern in it are affected by the requestmatchers
                .securityMatcher("/api/**", "/admin", "/login", "/logout")
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/admin").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/product/**", "/api/reservation/**","/api/item/**", "/api/item-reservation/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/reservation/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/auth/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/reservation/**").hasAuthority("ROLE_ADMIN")
                )

                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .permitAll()
                        .deleteCookies("JSESSIONID")
                )

                .exceptionHandling(Exception ->
                Exception.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))

                .csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
