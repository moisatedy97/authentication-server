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
import org.tedygabrielmoisa.authenticationserver.entities.User;
import org.tedygabrielmoisa.authenticationserver.enums.Role;
import org.tedygabrielmoisa.authenticationserver.repositories.UserRepository;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UsernamePasswordAuthenticationProvider implements AuthenticationProvider {
  private final SecurityUserDetailsService userService;
  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    String username = authentication.getName();
    String password = String.valueOf(authentication.getCredentials());

    UserDetails userDetails = userService.loadUserByUsername(username);
    Optional<User> dbUser = userRepository.findUserByEmail(username);

    if (dbUser.isPresent()) {
      User currentUser = dbUser.get();

      if (passwordEncoder.matches(password, userDetails.getPassword())) {
        if (currentUser.getRole().equals(Role.ADMIN)) {
          return new UsernamePasswordAuthentication(
                  userDetails,
                  null,
                  userDetails.getAuthorities()
          );
        } else {
          return new UsernamePasswordAuthentication(userDetails.getUsername(), userDetails.getPassword());
        }
      } else {
        throw new BadCredentialsException("Bad credentials!");
      }
    } else {
      throw new UsernameNotFoundException("Username not found: " + username);
    }
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return authentication.equals(UsernamePasswordAuthentication.class);
  }
}
