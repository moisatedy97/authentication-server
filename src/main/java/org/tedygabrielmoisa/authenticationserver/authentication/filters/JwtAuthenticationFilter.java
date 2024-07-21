package org.tedygabrielmoisa.authenticationserver.authentication.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.tedygabrielmoisa.authenticationserver.authentication.SecurityUserDetailsService;
import org.tedygabrielmoisa.authenticationserver.services.AuthenticationService;
import org.tedygabrielmoisa.authenticationserver.services.JwtService;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final AuthenticationService authenticationService;
    private final JwtService jwtService;
    private final SecurityUserDetailsService userService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        String jwtToken = authenticationService.getJwtToken(request.getHeader("Authorization"));

        if (jwtToken != null) {
            authenticateToken(request, jwtToken);
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(@NonNull HttpServletRequest request) throws ServletException {
        try {
            return request.getServletPath().equals("/auth/login") ||
                    request.getServletPath().equals("/auth/checkAuthenticated") ||
                    request.getServletPath().equals("/auth/register");
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private void authenticateToken(HttpServletRequest request, String token) throws IOException {
        final String subject = jwtService.extractJwtSubject(token);

        try {
            if (subject != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userService.loadUserByUsername(subject);

                if (jwtService.isTokenValid(token, userDetails.getUsername()) &&
                        jwtService.isRefreshTokenPreset(userDetails.getUsername())) {
                    setAuthentication(request, userDetails);
                }
            }
        } catch (Exception e) {
            throw new IOException("Error while authenticating the token for " + subject, e);
        }
    }

    private void setAuthentication(HttpServletRequest request, UserDetails userDetails) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );

        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}

