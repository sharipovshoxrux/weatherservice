package org.example.weather.config.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.example.weather.domain.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenUtil {
    private final JwtKeyUtil jwtKeyUtil;

    public JwtTokenUtil(JwtKeyUtil jwtKeyUtil1) {
        this.jwtKeyUtil = jwtKeyUtil1;
    }

    @Value("${application.security.jwt.expiration}")
    private Long jwtExpiryTime; //ms

    @Value("${application.security.jwt.refresh-time}")
    private Long jwtRefreshTime; //ms

    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getRole());
        return buildToken(claims, user, jwtExpiryTime);
    }
    public String generateRefreshToken(User user) {
        return buildToken(new HashMap<>(), user, jwtRefreshTime);
    }
    private String buildToken(Map<String, Object> extraClaims, User user, long expiration) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(jwtKeyUtil.getPrivateKey(), SignatureAlgorithm.RS256)
                .compact();
    }


}