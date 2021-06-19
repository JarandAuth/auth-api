package dev.jarand.authapi.oauth.service;

import dev.jarand.authapi.authentication.AuthenticationService;
import dev.jarand.authapi.oauth.domain.ClientCredentialsParameters;
import dev.jarand.authapi.oauth.domain.Tokens;
import dev.jarand.authapi.token.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientCredentialsService {

    private static final Logger logger = LoggerFactory.getLogger(ClientCredentialsService.class);

    private final AuthenticationService authenticationService;
    private final TokenService tokenService;

    public ClientCredentialsService(AuthenticationService authenticationService, TokenService tokenService) {
        this.authenticationService = authenticationService;
        this.tokenService = tokenService;
    }

    public Optional<Tokens> handle(ClientCredentialsParameters parameters) {
        final var clientId = parameters.getClientId();
        final var clientSecret = parameters.getClientSecret();
        logger.info("Performing client credentials flow for clientId: {}", clientId);
        if (!authenticationService.authenticate(clientId, clientSecret)) {
            logger.info("Cancelling client credentials flow (authentication failed) clientId: {}", clientId);
            return Optional.empty();
        }
        final var optionalTokens = tokenService.createTokens(clientId);
        if (optionalTokens.isEmpty()) {
            logger.info("Cancelling client credentials flow (token creation failed) for clientId: {}", clientId);
            return Optional.empty();
        }
        logger.info("Successful client credentials flow for clientId: {}", clientId);
        return optionalTokens;
    }
}
