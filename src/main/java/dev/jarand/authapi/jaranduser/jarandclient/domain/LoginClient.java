package dev.jarand.authapi.jaranduser.jarandclient.domain;

import java.time.Instant;
import java.util.UUID;

public class LoginClient {

    private final String clientId;
    private final String username;
    private final String password;
    private final UUID ownerId;
    private final Instant timeOfCreation;

    public LoginClient(String clientId, String username, String password, UUID ownerId, Instant timeOfCreation) {
        this.clientId = clientId;
        this.username = username;
        this.password = password;
        this.ownerId = ownerId;
        this.timeOfCreation = timeOfCreation;
    }

    public String getClientId() {
        return clientId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public UUID getOwnerId() {
        return ownerId;
    }

    public Instant getTimeOfCreation() {
        return timeOfCreation;
    }
}
