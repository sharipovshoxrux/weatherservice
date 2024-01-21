package org.example.weather.domain.dto.request.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginRequestDTO {
    @NotBlank(message = "username must not be blank")
    @Schema(description = "username", example = "manager_username")
    private String username;
    @NotBlank(message = "password must not be blank")
    @Schema(description = "password", example = "manager123")
    private String password;
}
