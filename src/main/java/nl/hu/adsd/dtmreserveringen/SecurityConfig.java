package nl.hu.adsd.dtmreserveringen;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
<<<<<<< HEAD
import org.springframework.http.HttpMethod;
=======
>>>>>>> L4-26-secure_admin_plus_login
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


import nl.hu.adsd.dtmreserveringen.services.AccountDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

<<<<<<< HEAD
        httpSecurity
            .securityMatcher("/api/**")
                .authorizeHttpRequests((auth) -> auth
                // the api endpoints and their authorization
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/product/**", "/api/item/**").permitAll()
                         .requestMatchers(HttpMethod.POST, "/api/reservation/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/product/**", "/api/item-reservation/**",
                         "/api/reservation/**", "api/item/**").hasRole("ADMIN")
                         .requestMatchers(HttpMethod.GET, "/api/reservation/all").hasRole("ADMIN")
                );

        httpSecurity
                .csrf(AbstractHttpConfigurer::disable);

        httpSecurity.formLogin((form) -> form
        .loginPage("/login").permitAll());
=======
        http
                .formLogin(form -> form
                        .loginPage("/login").permitAll()
                        .defaultSuccessUrl("/").permitAll()
//                        .failureUrl("/register").permitAll()
                )

                .sessionManagement(session -> session
                        .invalidSessionUrl("/")
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                        .sessionFixation(SessionManagementConfigurer.SessionFixationConfigurer::newSession) //find difference with migrateSession
                        .sessionFixation().migrateSession()
                        .maximumSessions(1) //Disables a user from login in multiple times in different tabs, replaces with the newest login
//                        .maxSessionsPreventsLogin(true) //Prevent further login attempts if the maximum sessions limit is reached
                        )

                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/", "/login", "/register").permitAll()
                        .requestMatchers("/admin").hasAuthority("ROLE_ADMIN")
                        .anyRequest().permitAll()
                )

                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .permitAll()
                        .deleteCookies("JSESSIONID")
                )
>>>>>>> L4-26-secure_admin_plus_login

                .csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }

    //https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/dao-authentication-provider.html
    // IT MAKES NO DIFFERENCE IF AUTHENTICATION MANAGER IS COMMENTED OUT
    @Bean
    public AuthenticationManager authenticationManager(AccountDetailsService accountDetailsService,
                                                       PasswordEncoder passwordEncoder) {

        System.out.println("authenticationManager IS IT USED??????! because it works without");
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(accountDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);

        return new ProviderManager(daoAuthenticationProvider);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
