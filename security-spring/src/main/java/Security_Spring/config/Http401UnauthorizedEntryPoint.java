package Security_Spring.config;

import Security_Spring.handlers.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.time.Instant;

@Configuration
@Slf4j // utilise LOMBOK pour la gestion des logs
//gestion des erreurs liees a l'authentification
public class Http401UnauthorizedEntryPoint implements AuthenticationEntryPoint {
    @Override//cette methode est appelee lorsque l'autheentification echoue
    public void commence(HttpServletRequest request, //requete entrante
                         HttpServletResponse response, // reponse a envoye au client
                         AuthenticationException authException // exception levee
    ) throws IOException, ServletException {
        log.error("Unauthorize error: {}", authException.getMessage()); //enregistre le message d'erreur dans les logs

        //configuration de lareponse
        response.setContentType(MediaType.APPLICATION_JSON_VALUE); //type du contenu de la reponse
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); //statu HTTP de la reponse

        //creation du code d'erreur present dans la reponse
        ErrorResponse body = ErrorResponse.builder()
                .status(HttpServletResponse.SC_UNAUTHORIZED) //status HTTP
                .error("Unauthorized") //description de l'erreur
                .timeStamp(Instant.now()) // instant de production de l'erreur
                .path(request.getServletPath()) //chemin de la requete responsable de l'erreur
                .build();

        final ObjectMapper mapper = new ObjectMapper(); //objet utilise pour convertir un objet JAVA en JSON (inversement)
        mapper.registerModule(new JavaTimeModule()); //module pour correctement mapper les objets lies au temps
        mapper.configure(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS, false);//configure le mapper pour afficher les dates de facon lisible
        mapper.writeValue(response.getOutputStream(), body);
    }
}
