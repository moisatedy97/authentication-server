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


    public String generateJwtToken(User user) {
        return generateJwtToken(new HashMap<>(), user);
    }

    public String generateJwtToken(Map<String, Object> extraClaims, User user) {
        return buildToken(extraClaims, user, jwtTokenLongevity * 1000);
    }

    public String generateJwtRefreshToken(User user) {
        return buildToken(new HashMap<>(), user, jwtRefreshTokenLongevity * 1000);
    }

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

    public boolean isTokenValid(String token, String username) {
        final String userDsxlog = extractJwtSubject(token);

        return (userDsxlog.equals(username)) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

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

    public String extractJwtSubject(String token) {
        try {
            return extractClaim(token, Claims::getSubject);
        } catch (Exception e) {
            return null;
        }
    }

    public Date extractExpiration(String token) {
        try {
            return extractClaim(token, Claims::getExpiration);
        } catch (Exception e) {
            return null;
        }
    }

    public int extractDuration(String token) {
        Date expiration = extractExpiration(token);
        Date now = new Date();

        return (int) ((expiration.getTime() - now.getTime()) / 1000);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);

        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecretKey);

        return Keys.hmacShaKeyFor(keyBytes);
    }
}
