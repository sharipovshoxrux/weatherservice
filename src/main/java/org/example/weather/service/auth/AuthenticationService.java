package org.example.weather.service.auth;

import org.example.weather.domain.dto.request.auth.LoginRequestDTO;
import org.example.weather.domain.dto.response.auth.AuthenticationResponseDTO;

public interface AuthenticationService {
    AuthenticationResponseDTO login(LoginRequestDTO request);
}
