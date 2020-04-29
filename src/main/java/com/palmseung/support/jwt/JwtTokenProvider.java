package com.palmseung.support.jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Date;

import static com.palmseung.support.Messages.WARNING_JWT_INVALID_TOKEN;

@Setter
@Getter
@Component
@EnableConfigurationProperties(ReadProperties.class)
public class JwtTokenProvider {
    private String secretKey;
    private Long expireLength;

    public JwtTokenProvider(ReadProperties readProperties) {
        this.secretKey = readProperties.getSecretKey();
        this.expireLength = readProperties.getExpireLength();
    }

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(String email) {
        Claims claims = Jwts.claims().setSubject(email);
        Date now = new Date();
        Date validity = new Date(now.getTime() + expireLength);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String extractEmail(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean isValidToken(String token) {
        try {
            if (getExpirationFromToken(token).after(new Date())) {
                return true;
            }
        } catch (RuntimeException e) {
            throw new IllegalArgumentException(WARNING_JWT_INVALID_TOKEN);
        }
        return false;
    }

    private Date getExpirationFromToken(String token) {
        Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
        return claims.getBody().getExpiration();
    }

    public Authentication extractAuthentication(String token) {
        return null;
    }
}