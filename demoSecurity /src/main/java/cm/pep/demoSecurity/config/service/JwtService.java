package cm.pep.demoSecurity.config.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    // chaîne encodée en Base64 qui représente la clé secrète utilisée pour signer les JWT
    private static  final String SECRET_KEY = "2qLKC828b=BWTTj3IFdvBSa5Q/nnZk3WdfZIOl=XUzV1Fv-Y1l51OBfX5K-Mw9urK8LEFy1FM6!/72sytKgC8J-JhdpyDmt=MH7cZ0TTZFIM4L?HDMDIUqtDVKzNK=?B3/Ml8sR!=ivb1i5ODH6l/59BmX9TsQWkkZ6uK2UtFL!zL/kKUN44zBb5i?meO3yxnr17lSd7hliweeL!YN9YxONLzK9=CFcbsdAEuCEOv4cNufGeQITdHoV99lO997!L";

    // pour decoder la cle de signature (SECRET_key) utilise pour signer les JWT
    private Key getSignInKey() {

        //décode scret_KEY en un tableau d'octets (byte[])
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);

        /*rend le tableau d'octets décodé et crée un objet Key qui sera utilisé pour
         signer et vérifier les JWT en utilisant l'algorithme HMAC-SHA256*/
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /*methodes utilisee pour extraire les informations supplémentaires
     qui seront presentent dans le token*/
    private Claims extractAllClaims(String token) {
        return Jwts
                /*le parser est  une composante ou un outil qui lit,
                décode et vérifie un JWT pour extraire et
                valider les revendications (claims) qu'il contient*/

                .parserBuilder() //creation du parser
                .setSigningKey(getSignInKey()) /*Configure le parseur JWT avec la clé de signature.
                                                 Cela permet au parseur de vérifier la signature
                                                     du token pour s'assurer qu'il est authentique.*/
                .build()
                .parseClaimsJws(token)/*analyse le token pour extraire ses composants  et
                                        verifie que le token n'a pas été modifie depuis sa création*/
                .getBody();// le parser extrait toute les informations du token

    }


    /*utilisee pour extraire une information specifique du token
        la methode peut retourner n'importe quel type
        String token = token dont on veut extraire l'information
        Function<Claims, T> claimsResolver = extrait une
        information(claims(revendication)) specifique et retourne son type
        */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){

        //pour obtenir toutes les claims du token
        final Claims claims = extractAllClaims(token);
        //pour obtenir la revendication specifique
        return claimsResolver.apply(claims);
    }

    // pour extraire le UserName du token garce a la methode extractClaim
    /*
        pour creer un token
        Map<String, Object> extractClaims = ensemble des claims a inclure dans le token
        UserDetails userDetails = objet contenant les details de l'utilisateur
    */
    public String generateToken(
            Map<String, Object> extractClaims,
            UserDetails userDetails
    ){
        return Jwts
                .builder() //pour creer le token
                .setClaims(extractClaims) //definir les claims a inclure dans le token
                .setSubject(userDetails.getUsername()) //pour definir le User auquel le token appartient
                .setIssuedAt(new Date(System.currentTimeMillis()))//defini la date d'emision du token
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))// defini la date d'expiration du token
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)//Spécifie la clé utilisée pour signer le token et l'algorithme de signature.
                .compact();//token final construit
    }

    public String extractUsername(String token) {
        //pour extraire le nom d'utilisateur
        return extractClaim(token, Claims::getSubject);
    }

    //creation du token lorsque aucune information (claims) n'est necessaire dans le token
    public String generateToken(UserDetails userDetails){
        //methode de creation du token
        return generateToken(new HashMap<>(), userDetails);
    }

    /*pour verifier si le token est valide pour un utilisateur donne
        String token = token a verifier
        UserDetails userDetails = Un objet contenant les détails de l'utilisateur,
    */
    public boolean isTokenValid(String token, UserDetails userDetails){
        //extrait le UserName du token
        final String userName = extractUsername(token);
        //verifie si le userNmae extrait correspond au userName passer dans UserDetails
        // et verifi si le token est encore valide
        //cela garati que le token appartient a userDetails
        return (userName.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    //verifie si le token a expire ou non
    private boolean isTokenExpired(String token) {
        //extractExpiration(token) : appelle la methode extractExpiration pour btenir la date d'expiration du token
        //before(new Date()) : Vérifie si la date d'expiration du token est antérieure à la date actuelle
        return extractExpiration(token).before(new Date());
    }

    //retourne la date d'expiration du token
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}
