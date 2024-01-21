package org.example.weather.config;

import lombok.var;
import org.example.weather.service.jwt.JwtService;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
class JwtAuthenticationFilter extends AuthenticationWebFilter {

    private UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtService jwtService, ReactiveAuthenticationManager authenticationManager, UserDetailsService userDetailsService) {
        super(authenticationManager);
        this.userDetailsService = userDetailsService;

        setServerAuthenticationConverter(exchange -> {
            String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            var username = jwtService.extractUsername(token);

            return Mono.justOrEmpty(jwtService.isJwtValid(token, userDetailsService.loadUserByUsername(String.valueOf(username))))
                    .map(valid -> new JwtAuthenticationToken(username, null));
        });
        setRequiresAuthenticationMatcher(ServerWebExchangeMatchers.pathMatchers("/api/v1/admin/**", "/api/v1/client/**"));
    }
}
