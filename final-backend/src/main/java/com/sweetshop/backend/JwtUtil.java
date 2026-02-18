package com.sweetshop.backend;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {

    // Forcing a default fallback string that is intentionally extremely long to guarantee it passes the 256-bit security check
    @Value("${JWT_SECRET:ThisIsAFallbackLocalKeyThatIsAtLeastThirtyTwoCharsLong!!}")
    private String secret;

    // --- NEW: Helper to generate a mathematically secure Key object ---
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    // --- 1. Extract Info from Token ---
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(getSigningKey()) // UPDATED: Use the secure Key object here
                .parseClaimsJws(token)
                .getBody();
    }

    // --- 2. Check if Token is Expired ---
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // --- 3. Generate Token ---
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours
                .signWith(getSigningKey(), SignatureAlgorithm.HS256) // UPDATED: Pass the secure Key object here
                .compact();
    }

    // --- 4. Validate Token ---
    public Boolean validateToken(String token) {
        try {
            final String username = extractUsername(token);
            return (username != null && !isTokenExpired(token));
        } catch (Exception e) {
            return false;
        }
    }
}
