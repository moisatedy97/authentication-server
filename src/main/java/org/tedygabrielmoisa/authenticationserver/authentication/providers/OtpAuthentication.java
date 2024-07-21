package org.tedygabrielmoisa.authenticationserver.authentication.providers;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * Represents an authentication object for OTP (One-Time Password) authentication. This class extends
 * {@link UsernamePasswordAuthenticationToken} to use OTP as credentials.
 */
public class OtpAuthentication extends UsernamePasswordAuthenticationToken {
    /**
     * Constructs an {@code OtpAuthentication} object with the provided principal and credentials.
     *
     * @param principal   the principal (typically a user identifier)
     * @param credentials the credentials (typically an OTP code)
     */
    public OtpAuthentication(Object principal, Object credentials) {
        super(principal, credentials);
    }

    /**
     * Constructs an {@code OtpAuthentication} object with the provided principal, credentials, and authorities.
     *
     * @param principal   the principal (typically a user identifier)
     * @param credentials the credentials (typically an OTP code)
     * @param authorities the authorities granted to the principal
     */
    public OtpAuthentication(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }
}
