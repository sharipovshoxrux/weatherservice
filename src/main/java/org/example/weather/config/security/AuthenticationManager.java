package org.example.weather.config.security;

import lombok.AllArgsConstructor;
import org.example.weather.config.jwt.JwtValidationUtil;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class AuthenticationManager implements ReactiveAuthenticationManager {

    private final JwtValidationUtil jwtValidationUtil;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String token = authentication.getCredentials().toString();
        String username = jwtValidationUtil.getUsername(token);
        return Mono.justOrEmpty(jwtValidationUtil.isTokenValid(token, username))
                .flatMap(isValid -> AuthenticateUserAndToken(username, token));

    }
    private Mono<Authentication> AuthenticateUserAndToken(String username, String token) {
        return Mono.just(jwtValidationUtil.getClaimsToken(token))
                .map(claims -> {
                    List<String> roleList = claims.get("role", List.class);
                    return new UsernamePasswordAuthenticationToken(
                            username,
                            null,
                            roleList.stream()
                                    .map(SimpleGrantedAuthority::new)
                                    .collect(Collectors.toList())
                    );
                });
    }
}