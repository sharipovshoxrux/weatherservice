package org.example.weather.config;

import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private final String principal;

    public JwtAuthenticationToken(Claims principal, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = String.valueOf(principal);
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }
}
