package dev.jarand.authapi.jaranduser.jarandclient.domain;

import java.time.Instant;
import java.util.UUID;

public class SecretClient extends JarandClient {

    private final String clientSecret;

    public SecretClient(String clientId, String type, UUID ownerId, Instant timeOfCreation, String clientSecret) {
        super(clientId, type, ownerId, timeOfCreation);
        this.clientSecret = clientSecret;
    }

    public String getClientSecret() {
        return clientSecret;
    }
}
