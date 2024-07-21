package org.tedygabrielmoisa.authenticationserver.authentication;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.tedygabrielmoisa.authenticationserver.entities.SecurityUser;
import org.tedygabrielmoisa.authenticationserver.entities.User;
import org.tedygabrielmoisa.authenticationserver.repositories.UserRepository;

import java.util.Optional;

/**
 * {@link UserDetailsService} implementation for loading {@link UserDetails} from the database.
 */
@Component
@RequiredArgsConstructor
public class SecurityUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    /**
     * Loads the user details by username (email).
     *
     * @param username the username identifying the user whose data is required
     * @return the {@link UserDetails} of the user
     * @throws UsernameNotFoundException if the user with the given username could not be found
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> dbUser = userRepository.findUserByEmail(username);

        if (dbUser.isPresent()) {
            return new SecurityUser(dbUser.get());
        } else {
            throw new UsernameNotFoundException("Username not found: " + username);
        }
    }
}
