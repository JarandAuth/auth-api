package dev.jarand.authapi.jaranduser.domain;

import java.time.Instant;
import java.util.UUID;

public class JarandUser {

    private final UUID id;
    private final String email;
    private final String username;
    private final String displayName;
    private final String password;
    private final Instant timeOfCreation;

    public JarandUser(UUID id, String email, String username, String displayName, String password, Instant timeOfCreation) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.displayName = displayName;
        this.password = password;
        this.timeOfCreation = timeOfCreation;
    }

    public UUID getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getPassword() {
        return password;
    }

    public Instant getTimeOfCreation() {
        return timeOfCreation;
    }
}
