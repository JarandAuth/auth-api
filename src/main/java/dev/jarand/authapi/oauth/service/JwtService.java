package dev.jarand.authapi.oauth.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;
import java.sql.Date;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
public class JwtService {

    private static final Logger logger = LoggerFactory.getLogger(JwtService.class);

    private final PrivateKey privateKey;
    private final String issuer;

    public JwtService(PrivateKey privateKey, @Value("${jwt.issuer}") String issuer) {
        this.privateKey = privateKey;
        this.issuer = issuer;
    }

    public String createAccessToken(String clientId, Optional<String> scope, Instant issuedAt, int expiresIn) {
        final var encodedJwt = Jwts.builder()
                .setSubject(clientId)
                .setIssuer(issuer)
                .setIssuedAt(Date.from(issuedAt))
                .setExpiration(Date.from(issuedAt.plus(expiresIn, ChronoUnit.SECONDS)))
                .claim("scope", scope.orElse(null))
                .claim("type", "ACCESS")
                .signWith(SignatureAlgorithm.RS512, privateKey)
                .compact();
        logger.info("Created access token for clientId: {}", clientId);
        return encodedJwt;
    }

    public String createRefreshToken(String clientId, Optional<String> scope, Instant issuedAt, int expiresIn, String jti) {
        final var encodedJwt = Jwts.builder()
                .setId(jti)
                .setSubject(clientId)
                .setIssuer(issuer)
                .setIssuedAt(Date.from(issuedAt))
                .setExpiration(Date.from(issuedAt.plus(expiresIn, ChronoUnit.SECONDS)))
                .claim("scope", scope.orElse(null))
                .claim("type", "REFRESH")
                .signWith(SignatureAlgorithm.RS512, privateKey)
                .compact();
        logger.info("Created refresh token for clientId: {}", clientId);
        return encodedJwt;
    }
}
