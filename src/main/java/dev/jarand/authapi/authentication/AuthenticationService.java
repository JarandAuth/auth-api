package dev.jarand.authapi.authentication;

import dev.jarand.authapi.jaranduser.jarandclient.JarandClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    private final JarandClientService jarandClientService;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationService(JarandClientService jarandClientService, PasswordEncoder passwordEncoder) {
        this.jarandClientService = jarandClientService;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean authenticate(String clientId, String clientSecret) {
        logger.info("Trying to authenticate client with clientId: {}", clientId);
        final var optionalClient = jarandClientService.getClient(clientId);
        if (optionalClient.isEmpty()) {
            logger.info("Authentication failed (no client found) for clientId: {}", clientId);
            return false;
        }
        final var client = optionalClient.get();
        if (!passwordEncoder.matches(clientSecret, client.getClientSecret())) {
            logger.info("Authentication failed (invalid client secret) for clientId: {}", clientId);
            return false;
        }
        logger.info("Authentication successful for clientId: {}", clientId);
        return true;
    }
}
