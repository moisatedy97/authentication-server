package org.tedygabrielmoisa.authenticationserver.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterUserDto {
  private String firstName;

  private String lastName;

  @Email
  @NotNull
  private String email;

  @NotNull
  @Size(min = 5, max = 60)
  private String password;
}
