package com.example.SecurityTest.configuration;

import ch.qos.logback.core.util.StringUtil;
import com.example.SecurityTest.service.JwtService;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        String jwt = jwtService.getJwtFromCookie(request);
        String authHeader = request.getHeader("Authorization");

        if (jwt == null && (authHeader == null || !authHeader.startsWith("BEARER "))){
            filterChain.doFilter(request, response);
            return;
        }
        if (jwt == null && authHeader.startsWith("BEARER ")){
            jwt = authHeader.substring(7);
        }

        String username = jwtService.extractUsername(jwt);

        if (StringUtils.isNotEmpty(username) && SecurityContextHolder.getContext().getAuthentication() == null){

            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            if (jwtService.isValid(jwt, userDetails)){
                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        username,
                        null,
                        userDetails.getAuthorities()
                );
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                securityContext.setAuthentication(authenticationToken);
                SecurityContextHolder.setContext(securityContext);
            }
        }
        filterChain.doFilter(request, response);
    }
}
