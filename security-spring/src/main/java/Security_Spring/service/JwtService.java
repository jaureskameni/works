package Security_Spring.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String extractUserName(String token);
    String generateToken(UserDetails userDetails);
    boolean isValid(String token, UserDetails userDetails);
    ResponseCookie generateJwtCookie(String jwt);

    String getJwtFromCookie(HttpServletRequest request);
    ResponseCookie getCleanJwtCookie();
}
