package com.example.SecurityTest.service.impl;

import com.example.SecurityTest.service.JwtService;
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

    @Value("${application.security.jwt.expiration}")
    private  Long expiration;
    @Value("${application.security.jwt.secret-key}")
    private  String secret_key;
    @Value("${application.security.jwt.cookie-name}")
    private  String jwtCookieName;

    public JwtServiceImpl() throws NoSuchAlgorithmException {
        KeyGenerator generator = KeyGenerator.getInstance("HmacSHA256");
        SecretKey secretKey = generator.generateKey();
        secret_key = Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }

     private SecretKey getSigningKey(){
         byte[] keyBytes = Decoders.BASE64.decode(secret_key);
         return Keys.hmacShaKeyFor(keyBytes);
     }

    @Override
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    private String generateToken(
            Map<String, Object> extractClaims,
            UserDetails userDetails
    ){
        return buildToken(extractClaims, userDetails, expiration);
    }

    private String buildToken(
            Map<String, Object> extractClaims,
            UserDetails userDetails,
            long expiration
    ){
        return Jwts
                .builder()
                .claims()
                .add(extractClaims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .and()
                .signWith(getSigningKey())
                .compact();
    }

    @Override
    public String extractUsername(String jwt) {
        return extractClaim(jwt, Claims::getSubject);
    }

    private <T> T extractClaim(String jwt, Function<Claims, T> claimsTFunction){
        final Claims claims = extractAllClaims(jwt);
        return claimsTFunction.apply(claims);
    }

    private Claims extractAllClaims(String jwt){
        return Jwts
                .parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(jwt)
                .getPayload();
    }

    @Override
    public boolean isValid(String jwt, UserDetails userDetails) {
        final var username = extractUsername(jwt);
        return username.equals(userDetails.getUsername())&& !isTokenExpired(jwt);
    }

    private boolean isTokenExpired(String jwt){
        return extractExpiration(jwt).before(new Date());
    }

    private Date extractExpiration(String jwt){
        return extractClaim(jwt, Claims::getExpiration);
    }



    @Override
    public ResponseCookie generateJwtCookie(String jwt) {
        return ResponseCookie
                .from(jwtCookieName, jwt)
                .path("/")
                .maxAge(24*60*60)
                .httpOnly(true)
                .secure(true)
                .sameSite("Strict")
                .build();
    }

    @Override
    public String getJwtFromCookie(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, jwtCookieName);
        if (cookie == null){
            return null;
        }
        return cookie.getValue();
    }

    @Override
    public ResponseCookie getCleanJwtCookie() {
        return ResponseCookie
                .from(jwtCookieName, "")
                .path("/")
                .maxAge(0)
                .httpOnly(true)
                .secure(true)
                .sameSite("Strict")
                .build();
    }
}
