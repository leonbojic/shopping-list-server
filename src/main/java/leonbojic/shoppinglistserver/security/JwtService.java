package leonbojic.shoppinglistserver.security;


import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.token.TokenService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;


@Service
public class JwtService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TokenService.class);

    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    private static final long EXPIRATION_TIME = 86400000;

    public String generateToken(UserDetails user) {
        final Map<String, Object> claims = new HashMap<>();

        claims.put("sub", user.getUsername());
        claims.put("role", user.getAuthorities().stream().findFirst().orElseThrow().getAuthority());

        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SECRET_KEY, SignatureAlgorithm.HS512)
                .compact();
    }

    public void validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token);
        } catch (ExpiredJwtException ex) {
            LOGGER.error("JWT expired", ex.getMessage());
            throw ex;
        } catch (IllegalArgumentException ex) {
            LOGGER.error("Token is null, empty or only whitespace", ex.getMessage());
            throw ex;
        } catch (MalformedJwtException ex) {
            LOGGER.error("JWT is invalid", ex.getMessage());
            throw ex;
        } catch (UnsupportedJwtException ex) {
            LOGGER.error("JWT is not supported", ex.getMessage());
            throw ex;
        }
    }

    public String getSubject(String token) {
        return parseClaims(token).getSubject();
    }

    private Claims parseClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token).getBody();
    }
}
