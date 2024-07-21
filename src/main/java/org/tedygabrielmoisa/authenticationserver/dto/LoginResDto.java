package org.tedygabrielmoisa.authenticationserver.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.tedygabrielmoisa.authenticationserver.entities.User;

/**
 * Data Transfer Object (DTO) for the login response.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResDto {

    /**
     * The authenticated user.
     */
    @JsonProperty("user")
    private User user;

    /**
     * The JWT token for the authenticated user.
     */
    @JsonProperty("token")
    private String token;
}
