package nl.hu.adsd.dtmreserveringen.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .securityMatcher("/api/**")
            .authorizeHttpRequests((reqeust) -> reqeust
                    .requestMatchers(HttpMethod.GET,"/reservation/**").permitAll()
                    .requestMatchers("/product/**").permitAll()
                    .anyRequest().permitAll()
                )
                .formLogin((form) -> form
                    .disable()
                )
                .csrf((csrf) -> csrf
                    .disable()
                );

        return http.build();     
    }
}
