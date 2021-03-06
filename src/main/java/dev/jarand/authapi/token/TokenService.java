package dev.jarand.authapi.token;

import dev.jarand.authapi.oauth.domain.Tokens;
import dev.jarand.authapi.token.domain.AccessTokenClaims;
import dev.jarand.authapi.token.domain.RefreshToken;
import dev.jarand.authapi.token.domain.RefreshTokenClaims;
import dev.jarand.authapi.token.repository.TokenRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

@Service
public class TokenService {

    private static final Logger logger = LoggerFactory.getLogger(TokenService.class);

    private final JwtService jwtService;
    private final TokenRepository tokenRepository;
    private final Supplier<UUID> uuidSupplier;
    private final Supplier<Instant> instantSupplier;
    private final int accessTokenExpiresIn;
    private final int refreshTokenExpiresIn;

    public TokenService(JwtService jwtService,
                        TokenRepository tokenRepository,
                        Supplier<UUID> uuidSupplier,
                        Supplier<Instant> instantSupplier,
                        @Value("${access-token.expires-in}") int accessTokenExpiresIn,
                        @Value("${refresh-token.expires-in}") int refreshTokenExpiresIn) {
        this.jwtService = jwtService;
        this.tokenRepository = tokenRepository;
        this.uuidSupplier = uuidSupplier;
        this.instantSupplier = instantSupplier;
        this.accessTokenExpiresIn = accessTokenExpiresIn;
        this.refreshTokenExpiresIn = refreshTokenExpiresIn;
    }

    public Optional<Tokens> createTokens(String clientId, Optional<String> scope) {
        logger.info("Creating tokens for clientId: {}", clientId);
        final var issuedAt = instantSupplier.get();
        final var refreshTokenJti = uuidSupplier.get().toString();
        final var accessToken = jwtService.createAccessToken(clientId, scope, issuedAt, accessTokenExpiresIn);
        final var refreshToken = jwtService.createRefreshToken(clientId, scope, issuedAt, refreshTokenExpiresIn, refreshTokenJti);
        tokenRepository.saveRefreshToken(refreshTokenJti, clientId, issuedAt);
        logger.info("Saved refresh token jti: {} with issuedAt: {} for clientId: {}", refreshTokenJti, issuedAt, clientId);
        logger.info("Successfully created tokens for clientId: {}", clientId);
        return Optional.of(new Tokens(accessToken, accessTokenExpiresIn, refreshToken));
    }

    public Optional<Tokens> createAccessToken(String clientId, Optional<String> scope) {
        logger.info("Creating access token for clientId: {}", clientId);
        final var issuedAt = instantSupplier.get();
        final var accessToken = jwtService.createAccessToken(clientId, scope, issuedAt, accessTokenExpiresIn);
        logger.info("Successfully created access token for clientId: {}", clientId);
        return Optional.of(new Tokens(accessToken, accessTokenExpiresIn, null));
    }

    public Optional<AccessTokenClaims> parseAccessToken(String accessToken) {
        return jwtService.parseAccessToken(accessToken);
    }

    public Optional<RefreshTokenClaims> parseRefreshToken(String refreshToken) {
        return jwtService.parseRefreshToken(refreshToken);
    }

    public List<RefreshToken> getRefreshTokens() {
        return tokenRepository.getRefreshTokens();
    }

    public List<RefreshToken> getRefreshTokens(String subject) {
        return tokenRepository.getRefreshTokens(subject);
    }

    public boolean isRefreshTokenRegistered(String jti) {
        return tokenRepository.exists(jti);
    }
}
