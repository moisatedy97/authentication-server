package org.tedygabrielmoisa.authenticationserver.services;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.tedygabrielmoisa.authenticationserver.authentication.providers.OtpAuthentication;
import org.tedygabrielmoisa.authenticationserver.authentication.providers.UsernamePasswordAuthentication;
import org.tedygabrielmoisa.authenticationserver.dto.LoginUserDto;
import org.tedygabrielmoisa.authenticationserver.entities.Otp;
import org.tedygabrielmoisa.authenticationserver.entities.Token;
import org.tedygabrielmoisa.authenticationserver.entities.User;
import org.tedygabrielmoisa.authenticationserver.repositories.OtpRepository;
import org.tedygabrielmoisa.authenticationserver.repositories.TokenRepository;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Service responsible for handling authentication-related operations.
 */
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    @Value("${application.security.otp.longevity}")
    private long otpLongevity;

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final TokenRepository tokenRepository;
    private final OtpRepository otpRepository;
    private final PasswordEncoder passwordEncoder;
    private final OtpService otpService;

    /**
     * Authenticates a user based on the provided login details.
     *
     * @param loginUserDto the login details including email, password, and/or OTP
     * @return the authentication object if authentication is successful, null otherwise
     */
    public Authentication authenticate(LoginUserDto loginUserDto) {
        if (loginUserDto.getOtp() == null && !loginUserDto.getPassword().isEmpty()) {
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthentication(loginUserDto.getEmail(), loginUserDto.getPassword())
            );
        }

        if (loginUserDto.getPassword() == null && !loginUserDto.getOtp().isEmpty()) {
            return authenticationManager.authenticate(
                    new OtpAuthentication(loginUserDto.getEmail(), loginUserDto.getOtp())
            );
        }

        return null;
    }

    /**
     * Extracts the JWT token from the authorization header.
     *
     * @param header the authorization header containing the JWT token
     * @return the extracted JWT token, or null if the header is invalid
     */
    public String getJwtToken(String header) {
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }

        return null;
    }

    /**
     * Revokes all valid tokens associated with the specified user.
     *
     * @param user the user whose tokens need to be revoked
     */
    public void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());

        if (validUserTokens.isEmpty()) {
            return;
        }

        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });

        tokenRepository.saveAll(validUserTokens);
    }

    /**
     * Saves a new JWT token for the specified user.
     *
     * @param user     the user for whom the token is to be saved
     * @param jwtToken the JWT token to be saved
     */
    public void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .expired(false)
                .revoked(false)
                .build();

        tokenRepository.save(token);
    }

    /**
     * Generates and saves a new OTP for the specified user.
     *
     * @param user the user for whom the OTP is to be generated
     */
    public void saveUserOtp(User user) {
        String code = otpService.generateOtp();
        System.out.println(code);
        Optional<Otp> dbOtp = otpRepository.findOtpByUserId(user.getId());

        Otp otp = dbOtp.orElseGet(() -> Otp.builder().user(user).build());

        otp.setCreatedAt(LocalDateTime.now());
        otp.setExpiresAt(LocalDateTime.now().plusSeconds(otpLongevity));
        otp.setOtp(passwordEncoder.encode(code));

        otpRepository.save(otp);
    }

    /**
     * Sets the JWT token as a cookie in the response.
     *
     * @param jwtToken the JWT token to be set as a cookie
     * @param response the HTTP response object
     */
    public void setCookieToken(String jwtToken, HttpServletResponse response) {
        response.addHeader("Set-Cookie", "token=" + jwtToken + "; HttpOnly; Path=/; Max-Age=" + jwtService.extractDuration(jwtToken));
    }
}
