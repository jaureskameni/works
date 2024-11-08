package com.example.SecurityTest.configuration;

import com.example.SecurityTest.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

@RequiredArgsConstructor
@Configuration
public class SecurityTestBean {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(){
        return new JwtAuthenticationFilter(jwtService, userDetailsService);
    }
}
