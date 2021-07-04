package dev.jarand.authapi.oauth;

import dev.jarand.authapi.grantedtype.GrantedTypeService;
import dev.jarand.authapi.jarandclient.JarandClientService;
import dev.jarand.authapi.jarandclient.domain.SecretClient;
import dev.jarand.authapi.oauth.domain.RefreshTokenParameters;
import dev.jarand.authapi.oauth.domain.Tokens;
import dev.jarand.authapi.scopeconnection.ScopeConnectionService;
import dev.jarand.authapi.token.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
public class RefreshTokenService {

    private static final Logger logger = LoggerFactory.getLogger(RefreshTokenService.class);

    private final JarandClientService jarandClientService;
    private final PasswordEncoder passwordEncoder;
    private final GrantedTypeService grantedTypeService;
    private final TokenService tokenService;
    private final ScopeConnectionService scopeConnectionService;

    public RefreshTokenService(JarandClientService jarandClientService,
                               PasswordEncoder passwordEncoder,
                               GrantedTypeService grantedTypeService,
                               TokenService tokenService,
                               ScopeConnectionService scopeConnectionService) {
        this.jarandClientService = jarandClientService;
        this.passwordEncoder = passwordEncoder;
        this.grantedTypeService = grantedTypeService;
        this.tokenService = tokenService;
        this.scopeConnectionService = scopeConnectionService;
    }

    public Optional<Tokens> handle(RefreshTokenParameters parameters) {
        logger.info("Performing refresh token flow");
        final var optionalRefreshTokenClaims = tokenService.parseRefreshToken(parameters.getRefreshToken());
        if (optionalRefreshTokenClaims.isEmpty()) {
            logger.info("Cancelling refresh token flow (token parsing failed)");
            return Optional.empty();
        }
        final var refreshTokenClaims = optionalRefreshTokenClaims.get();
        final var clientId = refreshTokenClaims.getSubject();
        if (!tokenService.isRefreshTokenRegistered(refreshTokenClaims.getJti())) {
            logger.info("Cancelling refresh token flow (refresh token not registered) for clientId: {}", clientId);
            return Optional.empty();
        }
        final var optionalClient = jarandClientService.getClient(clientId);
        if (optionalClient.isEmpty()) {
            logger.info("Cancelling refresh token flow (client not found) for clientId: {}", clientId);
            return Optional.empty();
        }
        final var client = optionalClient.get();
        if ("SECRET".equals(client.getType())) {
            final var secretClient = (SecretClient) client;
            final var clientSecretParameter = parameters.getClientSecret().orElseThrow(() -> new IllegalStateException("Secret client without secret"));
            if (!passwordEncoder.matches(clientSecretParameter, secretClient.getClientSecret())) {
                logger.info("Cancelling refresh token flow (secret mismatch) for clientId: {}", clientId);
                return Optional.empty();
            }
        }
        if (grantedTypeService.get("refresh_token", clientId).isEmpty()) {
            logger.info("Cancelling refresh token flow (unauthorized client) for clientId: {}", clientId);
            return Optional.empty();
        }
        final var optionalScope = refreshTokenClaims.getScope().map(scopeParam -> {
            final var scopeParams = Arrays.asList(scopeParam.split(" "));
            final var scopeConnections = scopeParams.stream()
                    .map(scope -> scopeConnectionService.get(scope, clientId))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .toList();
            final var scopesRequested = scopeParams.size();
            final var scopeConnectionsSize = scopeConnections.size();
            if (scopesRequested != scopeConnectionsSize) {
                logger.info(
                        "Mismatch between scopes in refresh token ({}) and scopes connected to client ({}) for clientId: {}",
                        scopesRequested, scopeConnectionsSize, clientId);
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
