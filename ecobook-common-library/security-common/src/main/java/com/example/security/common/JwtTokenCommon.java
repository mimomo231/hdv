package com.example.security.common;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.security.model.UserPrincipal;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtTokenCommon {

    @Value("${ecobook.app.jwtSecret}")
    private String jwtSecret;

    @Value("${ecobook.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    @Value("${ecobook.app.jwtRefreshExpirationMs}")
    private int jwtRefreshExpirationMs;

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public String generateJwtToken(UserPrincipal userPrincipal) {
        return generateTokenFromUsername(userPrincipal.getUsername(), userPrincipal.getId(), userPrincipal.getAuthorities());
    }

    public String generateJwtRefreshToken(UserPrincipal userPrincipal) {
        return generateRefreshTokenFromUsername(userPrincipal.getUsername());
    }

    public Algorithm algorithm() {
        return Algorithm.HMAC256(jwtSecret.getBytes());
    }

    public String generateTokenFromUsername(String username, Long userId, Collection<? extends GrantedAuthority> authorities) {
        return JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .withIssuedAt(new Date())
                .withClaim("roles", authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .withClaim("user_id", userId)
                .sign(algorithm());
    }

    public String generateRefreshTokenFromUsername(String username) {
        return JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis() + jwtRefreshExpirationMs))
                .withIssuedAt(new Date())
                .sign(algorithm());
    }
}
