package org.tedygabrielmoisa.authenticationserver.authentication.providers;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * Represents an authentication object for username and password authentication. This class extends
 * {@link UsernamePasswordAuthenticationToken} to use username and password as credentials.
 */
public class UsernamePasswordAuthentication extends UsernamePasswordAuthenticationToken {

    /**
     * Constructs a {@code UsernamePasswordAuthentication} object with the provided principal and credentials.
     *
     * @param principal   the principal (typically a user identifier)
     * @param credentials the credentials (typically a password)
     */
    public UsernamePasswordAuthentication(Object principal, Object credentials) {
        super(principal, credentials);
    }

    /**
     * Constructs a {@code UsernamePasswordAuthentication} object with the provided principal, credentials, and authorities.
     *
     * @param principal   the principal (typically a user identifier)
     * @param credentials the credentials (typically a password)
     * @param authorities the authorities granted to the principal
     */
    public UsernamePasswordAuthentication(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }
}
