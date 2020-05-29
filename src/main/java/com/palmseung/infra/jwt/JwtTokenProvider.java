package com.palmseung.infra.jwt;


import com.palmseung.infra.properties.TokenProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;

@Setter
@Getter
@Component
@EnableConfigurationProperties(TokenProperties.class)
public class JwtTokenProvider {
    private final static Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    private String secretKey;
    private Long expireLength;
    private UserDetailsService userDetailsService;

    public JwtTokenProvider(TokenProperties tokenProperties, UserDetailsService userDetailsService) {
        this.secretKey = tokenProperties.getSecretKey();
        this.expireLength = tokenProperties.getExpireLength();
        this.userDetailsService = userDetailsService;
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
            Jws<Claims> claimsJws = Jwts.parser()
                    .setSigningKey(this.secretKey)
                    .parseClaimsJws(token);
            logger.debug("token : {}", claimsJws);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public Authentication getAuthentication(String token) {
        String emailInToken = extractEmail(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(emailInToken);
        return new UsernamePasswordAuthenticationToken(userDetails, "", Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));
    }
}