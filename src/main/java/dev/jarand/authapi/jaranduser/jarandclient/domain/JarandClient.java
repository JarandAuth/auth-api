package dev.jarand.authapi.jaranduser.jarandclient.domain;

import java.time.Instant;
import java.util.UUID;

public class JarandClient {

    private final String clientId;
    private final String clientSecret;
    private final UUID ownerId;
    private final Instant timeOfCreation;

    public JarandClient(String clientId, String clientSecret, UUID ownerId, Instant timeOfCreation) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.ownerId = ownerId;
        this.timeOfCreation = timeOfCreation;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public UUID getOwnerId() {
        return ownerId;
    }

    public Instant getTimeOfCreation() {
        return timeOfCreation;
    }
}
