package org.tedygabrielmoisa.authenticationserver.enums;

/**
 * Enum representing the role of a user in the authentication server.
 */
public enum Role {
  /**
   * Represents a regular user.
   */
  USER,

  /**
   * Represents an administrator with elevated privileges.
   */
  ADMIN,

  /**
   * Represents a moderator with certain management privileges.
   */
  MODERATOR
}
