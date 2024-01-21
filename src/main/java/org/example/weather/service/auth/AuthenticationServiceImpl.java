package org.example.weather.service.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.weather.config.jwt.JwtTokenUtil;
import org.example.weather.config.security.UserPasswordEncoder;
import org.example.weather.domain.dto.request.auth.LoginRequestDTO;
import org.example.weather.domain.dto.response.auth.AuthenticationResponseDTO;
import org.example.weather.repository.UserRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepo;
    private final UserPasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;


    @Override
    public AuthenticationResponseDTO login(LoginRequestDTO request) {
        return userRepo.findByUsername(request.getUsername())
                .filter(user -> passwordEncoder.matches(request.getPassword(), user.getPassword()))
                .flatMap(user -> {
                    String jwt = jwtTokenUtil.generateToken(user);
                    return Mono.just(AuthenticationResponseDTO.builder()
                            .jwt(jwt)
                            .userId(user.getId())
                            .build());
                })
                .switchIfEmpty(Mono.error(new RuntimeException("Login failed - not found username or wrong password")))
                .block(); // Blocking operation to convert Mono to DTO
    }

}
