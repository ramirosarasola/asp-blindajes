package com.example.aspblindajes.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.example.aspblindajes.auth.Permissions.*;
import static com.example.aspblindajes.model.Role.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
//@EnableMethodSecurity // -> Habilita utilizar PreAuthorize en los controllers.
public class SecurityConfiguration {
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        authorizeHttpRequestCustomizer -> authorizeHttpRequestCustomizer
//                .requestMatchers(HttpMethod.POST, "/auth/register/").hasAnyAuthority(ADMIN.name(), ENGINEER.name())
                .requestMatchers(HttpMethod.POST, "/vehicle/**").hasAnyAuthority(CICO_LOGISTIC.name(), ADMIN.name(), ENGINEER.name())
                .requestMatchers("/vehicle/**").hasAnyAuthority(ADMIN.name(), ENGINEER.name())
                .requestMatchers("/brand/**").hasAnyAuthority(ADMIN.name(), ENGINEER.name())
                .requestMatchers("/model/**").hasAnyAuthority(ADMIN.name(), ENGINEER.name())
                .requestMatchers("/client/**").hasAnyAuthority(ADMIN.name(), ENGINEER.name())
                .requestMatchers("/workGroups/**").hasAnyAuthority(ADMIN.name(), ENGINEER.name())

                .requestMatchers("/vehicleQualityControl/**").hasRole(QUALITY_CONTROL.name())
                .requestMatchers(HttpMethod.POST, "/vehicleQualityControl/**").hasAuthority(QUALITY_CONTROL_CREATE.name())

                .anyRequest()
                .permitAll()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
