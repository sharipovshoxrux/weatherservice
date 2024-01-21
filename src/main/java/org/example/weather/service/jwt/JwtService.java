package org.example.weather.service.jwt;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;
import java.util.function.Function;

public interface JwtService {

    Claims extractUsername(String jwt);

    <T> Claims extractClaim(String jwt, Function<Claims, T> claimsResolver);

    String generateJwt(UserDetails userDetails);

    String generateJwt(Map<String, Object> extraClaims, UserDetails userDetails);

    boolean isJwtValid(String jwt, UserDetails userDetails);
}
