package org.tedygabrielmoisa.authenticationserver.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Data Transfer Object (DTO) for user registration requests.
 */
@Data
@AllArgsConstructor
public class RegisterUserDto {

  /**
   * The first name of the user.
   */
  private String firstName;

  /**
   * The last name of the user.
   */
  private String lastName;

  /**
   * The email of the user.
   */
  @Email
  @NotNull
  private String email;

  /**
   * The password of the user.
   */
  @NotNull
  @Size(min = 5, max = 60)
  private String password;
}
