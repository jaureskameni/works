package Security_Spring.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {

    private final JwtAuthenticationFilter authenticationFilter;
    private final AuthenticationProvider provider;
    private final Http401UnauthorizedEntryPoint unauthorizedEntryPoint;
    private final CustomAccessDeniedHandler accessDeniedHandler;

    private static final long MAX_AGE = 3600L;
    private static final int CORS_FILTER_ORDER = -10;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity ) throws Exception {
      return  httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(exception-> exception
                        .authenticationEntryPoint(unauthorizedEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler))
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
                                .requestMatchers(
                                        HttpMethod.POST, "api/v1/resource"
                                ).hasRole("ADMIN")
                                .anyRequest()
                                .authenticated()
                )
                .sessionManagement(manager-> manager.sessionCreationPolicy(STATELESS))
                .authenticationProvider(provider)
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
              .build();
    }

    @Bean //filter cors
    public FilterRegistrationBean<CorsFilter> filterRegistrationBean(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();//permet de definir les regles de cors basees sur l'url de la requete

        CorsConfiguration configuration = getCorsConfiguration();
        //Cette ligne enregistre la configuration CORS pour toutes les URL
        source.registerCorsConfiguration("/**", configuration);

        //Cela permet de traiter les requêtes avec les configurations CORS spécifiées
        FilterRegistrationBean<CorsFilter> registrationBean = new FilterRegistrationBean<>(new CorsFilter(source));

        //Définit l'ordre dans lequel le filtre sera exécuté.
        registrationBean.setOrder(CORS_FILTER_ORDER);

        //permet à Spring de gérer ce filtre dans le contexte de l'application.
        return registrationBean;
    }

    //pour confirurer les regles cors pour l'application
    private static CorsConfiguration getCorsConfiguration() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);//Permet d'envoyer des cookies ou des informations d'identification avec les requêtes entre différents domaines
        configuration.addAllowedOrigin("http://localhost:4200");//Définit les origines (domaines) autorisées à accéder aux ressources.
        //Spécifie les en-têtes qui peuvent être envoyés avec la requête
        configuration.setAllowedHeaders(Arrays.asList(
                HttpHeaders.AUTHORIZATION,
                HttpHeaders.CONTENT_TYPE,
                HttpHeaders.ACCEPT));
        //Définit les méthodes HTTP autorisées pour les requêtes CORS
        configuration.setAllowedMethods(Arrays.asList(
                HttpMethod.GET.name(),
                HttpMethod.POST.name(),
                HttpMethod.PUT.name(),
                HttpMethod.DELETE.name()));
        //Définit la durée (en secondes) pendant laquelle les résultats d'une requête prévol (preflight request) peuvent être mis en cache
        configuration.setMaxAge(MAX_AGE);
        return configuration;
    }

}
