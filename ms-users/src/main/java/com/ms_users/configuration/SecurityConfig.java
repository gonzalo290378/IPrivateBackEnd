package com.ms_users.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.GET, "/").permitAll()
                        .requestMatchers(HttpMethod.GET, "/authorized").permitAll()
                        .requestMatchers(HttpMethod.POST, "/").permitAll()
                        .requestMatchers(HttpMethod.GET, "/filter").permitAll()
                        .requestMatchers(HttpMethod.GET, "/email/{email}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/{username}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/check-availability-username/{username}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/check-availability-email/{email}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/new-account/register-page").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));

        return http.build();
    }
}
