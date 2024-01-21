package org.example.weather.service.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.example.weather.domain.dto.request.auth.LoginRequestDTO;
import org.example.weather.domain.dto.response.auth.AuthenticationResponseDTO;
import org.example.weather.exception.user.UserNotActiveException;
import org.example.weather.repository.UserRepository;
import org.example.weather.service.jwt.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepo;
    private final JwtService jwtService;
    private final ReactiveAuthenticationManager authManager;

    private static final int ACTIVE_USER = 1;

    @Override
    public AuthenticationResponseDTO login(LoginRequestDTO request) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        var user = userRepo.findByUsername(request.getUsername()).blockOptional()
                .orElseThrow(() -> new UsernameNotFoundException(
                        String.format("User with this username: %s not found", request.getUsername())));

        if (user.getStatus() != ACTIVE_USER) {
            throw new UserNotActiveException(
                    String.format("User with username: %s is not active. Try to login with active user!",
                            user.getUsername()));
        }

        var jwt = jwtService.generateJwt(user);
        return AuthenticationResponseDTO.builder()
                .jwt(jwt)
                .userId(user.getId())
                .build();
    }
}
