package org.tedygabrielmoisa.authenticationserver.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.tedygabrielmoisa.authenticationserver.entities.User;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResDto {
    @JsonProperty("user")
    private User user;

    @JsonProperty("token")
    private String token;
}
