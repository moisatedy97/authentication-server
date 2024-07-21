package org.tedygabrielmoisa.authenticationserver.authentication;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuration class for setting up a {@link DaoAuthenticationProvider}.
 * This provider retrieves user details from a {@link UserDetailsService} and verifies passwords using a {@link PasswordEncoder}.
 */
@Configuration
@RequiredArgsConstructor
public class DaoAuthenticationProviderConfig {
  private final UserDetailsService userDetailsService;
  private final PasswordEncoder passwordEncoder;

  /**
   * Configures and returns a {@link DaoAuthenticationProvider} bean.
   *
   * @return a configured {@link DaoAuthenticationProvider} bean
   */
  @Bean
  public DaoAuthenticationProvider daoAuthenticationProvider() {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

    provider.setUserDetailsService(userDetailsService);
    provider.setPasswordEncoder(passwordEncoder);

    return provider;
  }
}
