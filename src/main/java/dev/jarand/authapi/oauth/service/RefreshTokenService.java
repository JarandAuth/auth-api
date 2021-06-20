package dev.jarand.authapi.oauth.service;

import dev.jarand.authapi.authentication.AuthenticationService;
import dev.jarand.authapi.jaranduser.jarandclient.JarandClientService;
import dev.jarand.authapi.oauth.domain.RefreshTokenParameters;
import dev.jarand.authapi.oauth.domain.Tokens;
import dev.jarand.authapi.scope.ScopeConnectionService;
import dev.jarand.authapi.token.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
public class RefreshTokenService {

    private static final Logger logger = LoggerFactory.getLogger(RefreshTokenService.class);

    private final AuthenticationService authenticationService;
    private final JarandClientService jarandClientService;
    private final ScopeConnectionService scopeConnectionService;
    private final TokenService tokenService;
    private final JwtService jwtService;

    public RefreshTokenService(AuthenticationService authenticationService,
                               JarandClientService jarandClientService,
                               ScopeConnectionService scopeConnectionService,
                               TokenService tokenService,
                               JwtService jwtService) {
        this.authenticationService = authenticationService;
        this.jarandClientService = jarandClientService;
        this.scopeConnectionService = scopeConnectionService;
        this.tokenService = tokenService;
        this.jwtService = jwtService;
    }

    public Optional<Tokens> handle(RefreshTokenParameters parameters) {
        final var clientId = parameters.getClientId();
        final var clientSecret = parameters.getClientSecret();
        logger.info("Performing refresh token flow for clientId: {}", clientId);
        if (!authenticationService.authenticate(clientId, clientSecret)) {
            logger.info("Cancelling refresh token flow (authentication failed) for clientId: {}", clientId);
            return Optional.empty();
        }
        final var optionalRefreshTokenClaims = jwtService.parseRefreshToken(parameters.getRefreshToken());
        if (optionalRefreshTokenClaims.isEmpty()) {
            logger.info("Cancelling refresh token flow (token parsing failed) for clientId: {}", clientId);
            return Optional.empty();
        }
        final var refreshTokenClaims = optionalRefreshTokenClaims.get();
        if (!tokenService.isRefreshTokenRegistered(refreshTokenClaims.getJti())) {
            logger.info("Cancelling refresh token flow (refresh token not registered) for clientId: {}", clientId);
            return Optional.empty();
        }
        if (!refreshTokenClaims.getSubject().equals(clientId)) {
            logger.info("Cancelling refresh token flow (subject mismatch) for clientId: {}", clientId);
            return Optional.empty();
        }
        final var optionalScope = refreshTokenClaims.getScope().map(scopeParam -> {
            final var scopeParams = Arrays.asList(scopeParam.split(" "));
            final var jarandUserClient = jarandClientService.getClientByClientId(clientId).orElseThrow();
            final var scopeConnections = scopeParams.stream()
                    .map(scope -> scopeConnectionService.get(scope, jarandUserClient.getId()))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .toList();
            final var scopesRequested = scopeParams.size();
            final var scopeConnectionsSize = scopeConnections.size();
            if (scopesRequested != scopeConnectionsSize) {
                logger.info("Mismatch between scopes in refresh token ({}) and scopes connected to client ({}) for clientId: {}", scopesRequested, scopeConnectionsSize, clientId);
                return null;
            }
            logger.info("Validated {} scopes in refresh token for clientId: {}", scopeConnectionsSize, clientId);
            return scopeParam;
        });
        final var optionalTokens = tokenService.createAccessToken(clientId, optionalScope);
        if (optionalTokens.isEmpty()) {
            logger.info("Cancelling refresh token flow (token creation failed) for clientId: {}", clientId);
            return Optional.empty();
        }
        logger.info("Successful refresh token flow for clientId: {}", clientId);
        return optionalTokens;
    }
}
