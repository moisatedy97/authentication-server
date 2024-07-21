package org.tedygabrielmoisa.authenticationserver.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Data Transfer Object (DTO) for user login requests.
 */
@Data
@AllArgsConstructor
public class LoginUserDto {

    /**
     * The email of the user.
     */
    @Email
    @NotNull
    private String email;

    /**
     * The password of the user.
     */
    @Size(min = 5, max = 60)
    private String password;

    /**
     * The One-Time Password (OTP) of the user.
     */
    @Size(min = 4, max = 4)
    private String otp;
}
