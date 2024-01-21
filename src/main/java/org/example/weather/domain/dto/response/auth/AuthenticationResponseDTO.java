package org.example.weather.domain.dto.response.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticationResponseDTO {
    @Schema(description = "access token", example = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJDUk0tdXNlciIsImlhdCI6MTY3NTE3MDYzNywiZXhwIjoxNjc1MjU3MDM3fQ.7bbENavWOvFrqgs0hMhHSpU-r2L-IRBRb2eyz-uigMY4VQrNcJWS_9vlt7y6Trw9ia_mgNY1UGe-wI1thstH5A")
    private String jwt;
    @Schema(description = "ID of the user", example = "6591")
    private Long userId;
}
