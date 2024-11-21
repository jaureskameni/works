package com.example.SecurityTest.configuration.security;

import com.example.SecurityTest.configuration.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

    private final AuthenticationProvider provider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
       return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
               .authorizeHttpRequests(
                       request-> request
                               .requestMatchers(
                                       "/api/v1/auth/**",
                                       "/v2/api-docs",
                                       "/v3/api-docs",
                                       "/v3/api-docs/**",
                                       "/swagger-resources",
                                       "/swagger-resources/**",
                                       "/configuration/ui",
                                       "/configuration/security",
                                       "/swagger-ui/**",
                                       "/webjars/**",
                                       "/swagger-ui.html"
                               ).permitAll()
                               .requestMatchers(HttpMethod.POST, "api/v1/resource").hasRole("ADMIN")
                               .anyRequest().authenticated())
               .sessionManagement(manager-> manager.sessionCreationPolicy(STATELESS))
               .authenticationProvider(provider)
               .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
               .build();
    }
}
