package dev.jarand.authapi.jarandclient.domain;

import java.time.Instant;
import java.util.UUID;

public class JarandClient {

    private final UUID id;
    private final String clientId;
    private final String clientSecret;
    private final UUID ownerId;
    private final Instant timeOfCreation;

    public JarandClient(UUID id, String clientId, String clientSecret, UUID ownerId, Instant timeOfCreation) {
        this.id = id;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.ownerId = ownerId;
        this.timeOfCreation = timeOfCreation;
    }

    public UUID getId() {
        return id;
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
