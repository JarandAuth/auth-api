package dev.jarand.authapi.oauth.service;

import dev.jarand.authapi.authentication.AuthenticationService;
import dev.jarand.authapi.grantedtype.GrantedTypeService;
import dev.jarand.authapi.jaranduser.jarandclient.JarandClientService;
import dev.jarand.authapi.oauth.domain.ClientCredentialsParameters;
import dev.jarand.authapi.oauth.domain.Tokens;
import dev.jarand.authapi.scope.ScopeConnectionService;
import dev.jarand.authapi.token.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
public class ClientCredentialsService {

    private static final Logger logger = LoggerFactory.getLogger(ClientCredentialsService.class);

    private final AuthenticationService authenticationService;
    private final GrantedTypeService grantedTypeService;
    private final TokenService tokenService;
    private final JarandClientService jarandClientService;
    private final ScopeConnectionService scopeConnectionService;

    public ClientCredentialsService(AuthenticationService authenticationService,
                                    GrantedTypeService grantedTypeService,
                                    TokenService tokenService,
                                    JarandClientService jarandClientService,
                                    ScopeConnectionService scopeConnectionService) {
        this.authenticationService = authenticationService;
        this.grantedTypeService = grantedTypeService;
        this.tokenService = tokenService;
        this.jarandClientService = jarandClientService;
        this.scopeConnectionService = scopeConnectionService;
    }

    public Optional<Tokens> handle(ClientCredentialsParameters parameters) {
        final var clientId = parameters.getClientId();
        final var clientSecret = parameters.getClientSecret();
        logger.info("Performing client credentials flow for clientId: {}", clientId);
        if (!authenticationService.authenticate(clientId, clientSecret)) {
            logger.info("Cancelling client credentials flow (authentication failed) for clientId: {}", clientId);
            return Optional.empty();
        }
        final var jarandUserClient = jarandClientService.getClientByClientId(clientId).orElseThrow();
        if (grantedTypeService.get("client_credentials", jarandUserClient.getId()).isEmpty()) {
            logger.info("Cancelling client credentials flow (unauthorized client) for clientId: {}", clientId);
            return Optional.empty();
        }
        final var optionalScope = parameters.getScope().map(scopeParam -> {
            final var scopeParams = Arrays.asList(scopeParam.split(" "));
            final var scopeConnections = scopeParams.stream()
                    .map(scope -> scopeConnectionService.get(scope, jarandUserClient.getId()))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .toList();
            final var scopesRequested = scopeParams.size();
            final var scopeConnectionsSize = scopeConnections.size();
            if (scopesRequested != scopeConnectionsSize) {
                logger.info("Mismatch between scopes requested ({}) and scopes connected to client ({}) for clientId: {}", scopesRequested, scopeConnectionsSize, clientId);
                return null;
            }
            logger.info("Validated {} scopes requested for clientId: {}", scopeConnectionsSize, clientId);
            return scopeParam;
        });
        final var optionalTokens = tokenService.createTokens(clientId, optionalScope);
        if (optionalTokens.isEmpty()) {
            logger.info("Cancelling client credentials flow (token creation failed) for clientId: {}", clientId);
            return Optional.empty();
        }
        logger.info("Successful client credentials flow for clientId: {}", clientId);
        return optionalTokens;
    }
}
