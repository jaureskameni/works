package com.knj.SpringSecurity.service.impl;

import com.knj.SpringSecurity.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService {

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;
    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;
    @Value("${application.security.jwt.cookie-name}")
    private String jwtCookieName;

    public JwtServiceImpl() throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
        SecretKey secret_Key = keyGen.generateKey();
        secretKey = Base64.getEncoder().encodeToString(secret_Key.getEncoded());
    }
    
    @Override
    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username =extractUserName(token);
        return(username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return buildToken(extraClaims, userDetails,jwtExpiration);
    }

    @Override
    public ResponseCookie generateJwtCookie(String jwt) {
        return ResponseCookie
                .from(jwtCookieName, "")
                .path("/")
                .httpOnly(true)
                .maxAge(0)
                .build();
    }

    @Override
    public String getJwtFromCookies(HttpServletRequest request) {
            Cookie cookie = WebUtils.getCookie(request, jwtCookieName);
            if (cookie != null){
                return cookie.getValue();
            }
            return null;
    }

    @Override
    public ResponseCookie getCleanJwtCookie() {
        return ResponseCookie.from(jwtCookieName, "")
                .path("/")
                .httpOnly(true)
                .maxAge(0)
                .build();
    }



    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private String buildToken(
            Map<String, Object> extractClaims,
            UserDetails userDetails,
            long expiration
    ) {
        return  Jwts
                .builder() //pour creer le token
                .claims()
                .add(extractClaims) //definir les claims a inclure dans le token
                .subject(userDetails.getUsername()) //pour definir le User auquel le token appartient
                .issuedAt(new Date(System.currentTimeMillis()))//defini la date d'emision du token
                .expiration(new Date(System.currentTimeMillis() + expiration))// defini la date d'expiration du token
                .and()
                .signWith(getSigningKey())//Spécifie la clé utilisée pour signer le token et l'algorithme de signature.
                .compact();//token final construit
    }

    

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
