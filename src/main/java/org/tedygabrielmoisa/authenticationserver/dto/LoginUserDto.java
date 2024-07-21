package org.tedygabrielmoisa.authenticationserver.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class LoginUserDto {
    @Email
    @NotNull
    private String email;

    @Size(min = 5, max = 60)
    private String password;

    @Size(min = 4, max = 4)
    private String otp;
}
