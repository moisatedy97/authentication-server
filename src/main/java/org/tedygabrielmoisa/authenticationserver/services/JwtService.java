package org.tedygabrielmoisa.authenticationserver.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.tedygabrielmoisa.authenticationserver.entities.Token;
import org.tedygabrielmoisa.authenticationserver.entities.User;
import org.tedygabrielmoisa.authenticationserver.repositories.TokenRepository;
import org.tedygabrielmoisa.authenticationserver.repositories.UserRepository;

import java.security.Key;
import java.util.*;
import java.util.function.Function;

/**
 * Service responsible for handling JWT-related operations.
 */
@Service
@RequiredArgsConstructor
public class JwtService {

    @Value("${application.security.jwt.longevity}")
    private long jwtTokenLongevity;
    @Value("${application.security.jwt.refresh-token.longevity}")
    private long jwtRefreshTokenLongevity;
    @Value("${application.security.jwt.secret-key}")
    private String jwtSecretKey;

    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;

    /**
     * Generates a JWT token for the specified user with default claims.
     *
     * @param user the user for whom the token is being generated
     * @return the generated JWT token
     */
    public String generateJwtToken(User user) {
        return generateJwtToken(new HashMap<>(), user);
    }

    /**
     * Generates a JWT token for the specified user with additional claims.
     *
     * @param extraClaims additional claims to be included in the token
     * @param user the user for whom the token is being generated
     * @return the generated JWT token
     */
    public String generateJwtToken(Map<String, Object> extraClaims, User user) {
        return buildToken(extraClaims, user, jwtTokenLongevity * 1000);
    }

    /**
     * Generates a JWT refresh token for the specified user.
     *
     * @param user the user for whom the refresh token is being generated
     * @return the generated JWT refresh token
     */
    public String generateJwtRefreshToken(User user) {
        return buildToken(new HashMap<>(), user, jwtRefreshTokenLongevity * 1000);
    }

    /**
     * Builds a JWT token with the specified claims, user, and expiration time.
     *
     * @param extraClaims additional claims to be included in the token
     * @param user the user for whom the token is being generated
     * @param expiration the expiration time in milliseconds
     * @return the generated JWT token
     */
    private String buildToken(Map<String, Object> extraClaims, User user, long expiration) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(user.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Validates the JWT token for the specified username.
     *
     * @param token the JWT token to be validated
     * @param username the username to validate against
     * @return true if the token is valid, false otherwise
     */
    public boolean isTokenValid(String token, String username) {
        final String userDsxlog = extractJwtSubject(token);
        return (userDsxlog.equals(username)) && !isTokenExpired(token);
    }

    /**
     * Checks if the JWT token is expired.
     *
     * @param token the JWT token to be checked
     * @return true if the token is expired, false otherwise
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Checks if a refresh token is present for the specified user email.
     *
     * @param userEmail the user's email
     * @return true if a refresh token exists, false otherwise
     */
    public boolean isRefreshTokenPreset(String userEmail) {
        Optional<User> user = userRepository.findUserByEmail(userEmail);
        if (user.isEmpty()) {
            return false;
        }
        Optional<List<Token>> dbToken = tokenRepository.findByUserId(user.get().getId());
        if (dbToken.isPresent()) {
            List<Token> tokenList = dbToken.get();
            return tokenList.stream().anyMatch(tck -> !tck.isExpired() && !tck.isRevoked());
        }
        return false;
    }

    /**
     * Extracts the JWT token from the HTTP request cookies.
     *
     * @param request the HTTP request
     * @return the extracted JWT token, or null if not found
     */
    public String getCookieToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    /**
     * Extracts the subject (username) from the JWT token.
     *
     * @param token the JWT token
     * @return the subject, or null if extraction fails
     */
    public String extractJwtSubject(String token) {
        try {
            return extractClaim(token, Claims::getSubject);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Extracts the expiration date from the JWT token.
     *
     * @param token the JWT token
     * @return the expiration date, or null if extraction fails
     */
    public Date extractExpiration(String token) {
        try {
            return extractClaim(token, Claims::getExpiration);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Extracts the duration (in seconds) until the token's expiration.
     *
     * @param token the JWT token
     * @return the duration in seconds
     */
    public int extractDuration(String token) {
        Date expiration = extractExpiration(token);
        Date now = new Date();
        return (int) ((expiration.getTime() - now.getTime()) / 1000);
    }

    /**
     * Extracts a specific claim from the JWT token.
     *
     * @param token the JWT token
     * @param claimsResolver the function to extract the claim
     * @param <T> the type of the claim
     * @return the extracted claim
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extracts all claims from the JWT token.
     *
     * @param token the JWT token
     * @return the claims
     */
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Gets the signing key from the secret key.
     *
     * @return the signing key
     */
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
