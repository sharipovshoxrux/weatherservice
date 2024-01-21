package org.example.weather.config;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.var;
import org.example.weather.service.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Collections;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private ReactiveAuthenticationManager authenticationManager;

    @Value("${jwt.master-key}")
    private String masterKey;

    @Autowired
    public void setJwtService(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http
                .authorizeExchange()
                .pathMatchers("/api/v1/admin/**").hasAuthority("ADMIN")
                .pathMatchers("/api/v1/client/**").hasAuthority("USER")
                .anyExchange().permitAll()
                .and()
                .csrf().disable()
                .httpBasic().disable()
                .authenticationManager(authenticationManager)
                .addFilterAt(new JwtAuthenticationFilter(jwtService, authenticationManager, this.userDetailsService), SecurityWebFiltersOrder.AUTHENTICATION);

        return http.build();
    }

    @Bean
    public ReactiveAuthenticationManager authenticationManager() {
        return authentication -> {
            Object credentials = authentication.getCredentials();
            if (credentials instanceof String) {
                String token = (String) credentials;
                var username = jwtService.extractUsername(token);
                UserDetails userDetails = userDetailsService.loadUserByUsername(String.valueOf(username));
                // Validate master token
                if (masterKey.equals(token)) {
                    Authentication authenticated = new CustomAuthenticationToken(
                            "admin",
                            null,
                            Collections.singleton(new SimpleGrantedAuthority("ADMIN"))
                    );
                    return Mono.just(authenticated);
                }
                // Validate JWT token
                else if (jwtService.isJwtValid(token, userDetails)) {
                    Claims claims = jwtService.extractClaim(token, Claims::getSubject);
                    String role = claims.get("role", String.class);
                    Authentication authenticated = new CustomAuthenticationToken(
                            username,
                            null,
                            Collections.singleton(new SimpleGrantedAuthority(role))
                    );
                    return Mono.just(authenticated);
                }
            }
            return Mono.empty();
        };
    }

    @Autowired
    public void setAuthenticationManager(ReactiveAuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }
}


