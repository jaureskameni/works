package cm.pep.timeTable.security.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Service
public class JwtService {
    private static String SECRET_KEY = "2qLKC828b=BWTTj3IFdvBSa5Q/nnZk3WdfZIOl=XUzV1Fv-Y1l51OBfX5K-Mw9urK8LEFy1FM6!/72sytKgC8J-JhdpyDmt=MH7cZ0TTZFIM4L?HDMDIUqtDVKzNK=?B3/Ml8sR!=ivb1i5ODH6l/59BmX9TsQWkkZ6uK2UtFL!zL/kKUN44zBb5i?meO3yxnr17lSd7hliweeL!YN9YxONLzK9=CFcbsdAEuCEOv4cNufGeQITdHoV99lO997!L";

    public JwtService() throws NoSuchAlgorithmException {
        KeyGenerator keyGen =KeyGenerator.getInstance("HmacSHA256");
        SecretKey secretKey =keyGen.generateKey();
        SECRET_KEY =Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }
    public SecretKey getSignKey() {
        byte[] keyBytes = Decoders.BASE64URL.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(
            Map<String, Objects> extractClaims,
            UserDetails userDetails
    ){
        return Jwts
                .builder()
                .claims()
                .add(extractClaims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000*60*24))
                .and()
                .signWith(getSignKey())
                .compact();
    }

    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(), userDetails);
    }

    public Claims extractAllClaims(String jwtToken) {
        return Jwts
                .parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(jwtToken)
                .getPayload();
    }

    public  <T> T extractClaim(String jwtToken, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(jwtToken);
        return claimsResolver.apply(claims);
    }

    public String extractUsername(String jwtToken) {
        return extractClaim(jwtToken, Claims::getSubject);
    }

    public boolean isTokenValid(String jwtToken, UserDetails userDetails) {
        final String username = extractUsername(jwtToken);

        return (username.equals(userDetails.getUsername()) && !isTokenExpired(jwtToken));
    }

    private boolean isTokenExpired(String jwtToken) {
        return extractExpiration(jwtToken).before(new Date());
    }

    private Date extractExpiration(String jwtToken) {
        return extractClaim(jwtToken, Claims::getExpiration);
    }
}
