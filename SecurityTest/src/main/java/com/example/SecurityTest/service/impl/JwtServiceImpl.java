package com.example.SecurityTest.service.impl;

import com.example.SecurityTest.service.JwtService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtServiceImpl implements JwtService {

    @Value("${application.security.jwt.secret-key}")
    private  String secret_key;
    @Value("${application.security.jwt.expiration}")
    private  Long expiration;

    public JwtServiceImpl() throws NoSuchAlgorithmException {
        KeyGenerator generator = KeyGenerator.getInstance("HmacSHA256");
        SecretKey secretKey = generator.generateKey();
        secret_key = Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }
     private Key getSigning(){
         byte[] keyBytes = Decoders.BASE64.decode(secret_key);
         return Keys.hmacShaKeyFor(keyBytes);
     }

    @Override
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    @Override
    public String extractUsername(String jwt) {
        return "";
    }

    @Override
    public boolean isValid(String jwt, UserDetails userDetails) {
        return false;
    }

    private String generateToken(
            Map<String, Object> extractClaims,
            UserDetails userDetails
    ){
        return builToken(extractClaims, userDetails, expiration);
    }

    private String builToken(
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
    public ResponseCookie generateJwtCookie(String jwt) {
        return null;
    }

    @Override
    public String getJwtFromCookie(HttpServletRequest request) {
        return "";
    }

    @Override
    public ResponseCookie getCleanJwtCookie() {
        return null;
    }
}
