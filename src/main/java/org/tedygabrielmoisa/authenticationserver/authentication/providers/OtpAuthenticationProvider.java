package org.tedygabrielmoisa.authenticationserver.authentication.providers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.tedygabrielmoisa.authenticationserver.authentication.SecurityUserDetailsService;
import org.tedygabrielmoisa.authenticationserver.entities.Otp;
import org.tedygabrielmoisa.authenticationserver.repositories.OtpRepository;
import org.tedygabrielmoisa.authenticationserver.services.OtpService;

import java.util.Optional;

/**
 * {@link AuthenticationProvider} implementation for OTP (One-Time Password) authentication.
 * This provider handles authentication by validating the OTP for a given user.
 */
@Component
@RequiredArgsConstructor
public class OtpAuthenticationProvider implements AuthenticationProvider {
    private final SecurityUserDetailsService userService;
    private final OtpRepository otpRepository;
    private final OtpService otpService;
    private final PasswordEncoder passwordEncoder;

    /**
     * Performs authentication with the given authentication request.
     *
     * @param authentication the authentication request object
     * @return a fully authenticated object including credentials if authentication is successful
     * @throws AuthenticationException if authentication fails
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = String.valueOf(authentication.getCredentials());

        UserDetails userDetails = userService.loadUserByUsername(username);
        Optional<Otp> dbOtp = otpRepository.findOtpByUserEmail(username);

        if (dbOtp.isPresent()) {
            Otp currentOtp = dbOtp.get();

            if (otpService.checkOtpIsValid(currentOtp)) {
                if (passwordEncoder.matches(password, currentOtp.getOtp())) {
                    return new OtpAuthentication(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                }
            }

            throw new BadCredentialsException("Bad credentials!");
        } else {
            throw new UsernameNotFoundException("Username not found: " + username);
        }
    }

    /**
     * Indicates whether this provider supports the indicated authentication object.
     *
     * @param authentication a class of the type of authentication object this provider supports
     * @return true if this authentication provider supports the indicated authentication object
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(OtpAuthentication.class);
    }
}
