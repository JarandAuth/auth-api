package dev.jarand.authapi.oauth.service;

import dev.jarand.authapi.grantedtype.GrantedTypeService;
import dev.jarand.authapi.jaranduser.jarandclient.LoginClientService;
import dev.jarand.authapi.oauth.domain.PasswordParameters;
import dev.jarand.authapi.oauth.domain.Tokens;
import dev.jarand.authapi.scope.ScopeConnectionService;
import dev.jarand.authapi.token.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
public class PasswordService {

    private static final Logger logger = LoggerFactory.getLogger(PasswordService.class);

    private final LoginClientService loginClientService;
    private final PasswordEncoder passwordEncoder;
    private final GrantedTypeService grantedTypeService;
    private final ScopeConnectionService scopeConnectionService;
    private final TokenService tokenService;

    public PasswordService(LoginClientService loginClientService,
                           PasswordEncoder passwordEncoder,
                           GrantedTypeService grantedTypeService,
                           ScopeConnectionService scopeConnectionService,
                           TokenService tokenService) {
        this.loginClientService = loginClientService;
        this.passwordEncoder = passwordEncoder;
        this.grantedTypeService = grantedTypeService;
        this.scopeConnectionService = scopeConnectionService;
        this.tokenService = tokenService;
    }

    public Optional<Tokens> handle(PasswordParameters parameters) {
        final var username = parameters.getUsername();
        final var password = parameters.getPassword();
        logger.info("Performing password flow for username: {}", username);
        final var optionalLoginClient = loginClientService.getClient(username);
        if (optionalLoginClient.isEmpty()) {
            logger.info("Cancelling password flow (login client not found) for username: {}", username);
            return Optional.empty();
        }
        final var loginClient = optionalLoginClient.get();
        if (!passwordEncoder.matches(password, loginClient.getPassword())) {
            logger.info("Cancelling password flow (password mismatch) for username: {}", username);
            return Optional.empty();
        }
        final var clientId = loginClient.getClientId();
        if (grantedTypeService.get("password", clientId).isEmpty()) {
            logger.info("Cancelling password flow (unauthorized client) for clientId: {} and username: {}", clientId, username);
            return Optional.empty();
        }
        final var optionalScope = parameters.getScope().map(scopeParam -> {
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
                        "Mismatch between scopes requested ({}) and scopes connected to client ({}) for clientId: {} and username: {}",
                        scopesRequested, scopeConnectionsSize, clientId, username);
                return null;
            }
            logger.info("Validated {} scopes requested for clientId: {} and username: {}", scopeConnectionsSize, clientId, username);
            return scopeParam;
        });
        final var optionalTokens = tokenService.createTokens(clientId, optionalScope);
        if (optionalTokens.isEmpty()) {
            logger.info("Cancelling password flow (token creation failed) for clientId: {} and username: {}", clientId, username);
            return Optional.empty();
        }
        logger.info("Successful password flow for clientId: {} and username: {}", clientId, username);
        return optionalTokens;
    }
}
