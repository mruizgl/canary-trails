package es.iespuertodelacruz.mp.canarytrails.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration; //Aprox 3 meses //86400 24h

    public String generateToken(String username, String rol) {
        return JWT.create()
                .withSubject(username)
                .withClaim("role", rol)
                .withExpiresAt(new Date(System.currentTimeMillis() + expiration))
                .sign(Algorithm.HMAC256(secret));
    }


    public Map<String, String> validateAndGetClaims(String token) {
        Map<String, Claim> claims = JWT.require(Algorithm.HMAC256(secret))
                .build()
                .verify(token)
                .getClaims();

        Map<String,String> infoToken = new HashMap<String,String>();
        infoToken.put("username", claims.get("sub").asString());
        infoToken.put("role", claims.get("role").asString());

        return infoToken;
    }
}
