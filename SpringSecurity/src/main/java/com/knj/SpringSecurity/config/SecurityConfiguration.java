package com.knj.SpringSecurity.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {
    private final JwtAuthenticationFilter authenticationFilter;
    private final AuthenticationProvider provider;
    private final Http401UnauthorizedEntryPoint unauthorizedEntryPoint;
    private final CustomAccessDeniedHandler accessDeniedHandler;

    private static final Long MAX_AGE =3600L;
    private static final int CORS_FILTER_ORDER = -102;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(exception-> exception
                        .authenticationEntryPoint(unauthorizedEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler))
                .authorizeHttpRequests(
                        request-> request
                                .requestMatchers(
                                        "/api/v1/auth/**"
                                ).permitAll()
                                .requestMatchers(
                                        HttpMethod.POST,"/api/v1/resource"
                                ).hasRole("ADMIN")
                                .anyRequest().authenticated()
                )
                .sessionManagement(manager-> manager.sessionCreationPolicy(STATELESS))
                .authenticationProvider(provider)
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);

return httpSecurity.build();

    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean(){

        UrlBasedCorsConfigurationSource source =new UrlBasedCorsConfigurationSource();

        CorsConfiguration configuration =new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.addAllowedOrigin("http://localhost:4200");
        configuration.setAllowedHeaders(Arrays.asList(
                HttpHeaders.AUTHORIZATION,
                HttpHeaders.CONTENT_TYPE,
                HttpHeaders.ACCEPT));
        configuration.setAllowedMethods(Arrays.asList(
                HttpMethod.GET.name(),
                HttpMethod.POST.name(),
                HttpMethod.PUT.name(),
                HttpMethod.DELETE.name()));
        configuration.setMaxAge(MAX_AGE);
        source.registerCorsConfiguration("/**", configuration);

        FilterRegistrationBean registrationBean =new FilterRegistrationBean(new CorsFilter(source));

        registrationBean.setOrder(CORS_FILTER_ORDER);

        return registrationBean;
    }
}
