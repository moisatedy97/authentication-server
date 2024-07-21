package org.tedygabrielmoisa.authenticationserver.entities;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * Implementation of {@link UserDetails} for Spring Security that wraps a {@link User} entity.
 */
@AllArgsConstructor
public class SecurityUser implements UserDetails {
  private final User user;

  /**
   * Returns the authorities granted to the user.
   *
   * @return a collection of granted authorities
   */
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(user.getRole().name()));
  }

  /**
   * Returns the password used to authenticate the user.
   *
   * @return the password
   */
  @Override
  public String getPassword() {
    return user.getPasswordHash();
  }

  /**
   * Returns the username used to authenticate the user.
   *
   * @return the username
   */
  @Override
  public String getUsername() {
    return user.getEmail();
  }

  /**
   * Indicates whether the user's account has expired.
   *
   * @return {@code true} if the user's account is valid (i.e., non-expired), {@code false} if no longer valid (i.e., expired)
   */
  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  /**
   * Indicates whether the user is locked or unlocked.
   *
   * @return {@code true} if the user is not locked, {@code false} otherwise
   */
  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  /**
   * Indicates whether the user's credentials (password) has expired.
   *
   * @return {@code true} if the user's credentials are valid (i.e., non-expired), {@code false} if no longer valid (i.e., expired)
   */
  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  /**
   * Indicates whether the user is enabled or disabled.
   *
   * @return {@code true} if the user is enabled, {@code false} otherwise
   */
  @Override
  public boolean isEnabled() {
    return true;
  }
}
