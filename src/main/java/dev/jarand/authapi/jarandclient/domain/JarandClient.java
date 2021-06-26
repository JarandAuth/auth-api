package dev.jarand.authapi.jarandclient.domain;

import java.time.Instant;
import java.util.UUID;

public class JarandClient {

    private final String clientId;
    private final String type;
    private final UUID ownerId;
    private final Instant timeOfCreation;

    public JarandClient(String clientId, String type, UUID ownerId, Instant timeOfCreation) {
        this.clientId = clientId;
        this.type = type;
        this.ownerId = ownerId;
        this.timeOfCreation = timeOfCreation;
    }

    public String getClientId() {
        return clientId;
    }

    public String getType() {
        return type;
    }

    public UUID getOwnerId() {
        return ownerId;
    }

    public Instant getTimeOfCreation() {
        return timeOfCreation;
    }
}
