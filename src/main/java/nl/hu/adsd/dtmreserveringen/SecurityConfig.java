package nl.hu.adsd.dtmreserveringen;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import nl.hu.adsd.dtmreserveringen.services.AccountDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

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

        return httpSecurity.build();
    }

    //https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/dao-authentication-provider.html
    @Bean
    public AuthenticationManager authenticationManager(AccountDetailsService accountDetailsService,
                                                       PasswordEncoder passwordEncoder) {

        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(accountDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);

        return new ProviderManager(daoAuthenticationProvider);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    //listener used to prevent a user from having multiple sessions (auto invalidation), see filter-chain sessionManagement
    //https://docs.spring.io/spring-security/reference/servlet/authentication/session-management.html
    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }


    //TODO: https://docs.spring.io/spring-security/reference/servlet/exploits/csrf.html
    //TODO: https://docs.spring.io/spring-security/reference/servlet/authentication/architecture.html#servlet-authentication-providermanager
    //TODO: https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/index.html
    //TODO: https://docs.spring.io/spring-security/reference/servlet/authorization/authorize-http-requests.html

}
