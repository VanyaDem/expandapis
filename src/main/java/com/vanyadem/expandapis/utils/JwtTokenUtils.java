package com.vanyadem.expandapis.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Date;

/**
 * Utility class for generating and processing JSON Web Tokens (JWTs).
 */
@Component
public class JwtTokenUtils {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.lifetime}")
    private Duration jwtLifetime;


    /**
     * Generates a JWT token for the provided UserDetails object, typically representing
     * user authentication information.
     *
     * @param userDetails The UserDetails object containing user authentication details.
     * @return A JWT token as a String.
     * @author Vanya Demydenko
     */
    public String generateToken(UserDetails userDetails) {
        Date issuedDate = new Date();
        Date expiredDate = new Date(issuedDate.getTime() + jwtLifetime.toMillis());

        return buildJwt(userDetails.getUsername(), issuedDate, expiredDate);
    }

    /**
     * Retrieves the name from a JWT token.
     *
     * @param token The JWT token from which to extract the name.
     * @return The name contained in the token.
     * @author Vanya Demydenko
     */
    public String getUsername(String token) {
        return getAllClaimsFromToken(token).getSubject();
    }

    /**
     * Parses a JWT token, verifies its signature, and retrieves the claims contained within the token.
     */
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * Generates a secret key for signing and verifying JWT tokens using the configured secret.
     */
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Builds a JWT token with the provided subject, claims, issued date, and expiration date.
     */
    private String buildJwt(String subject, Date issuedDate, Date expiredDate) {
        return Jwts.builder()
                .subject(subject)
                .issuedAt(issuedDate)
                .expiration(expiredDate)
                .signWith(getSigningKey())
                .compact();
    }
}
