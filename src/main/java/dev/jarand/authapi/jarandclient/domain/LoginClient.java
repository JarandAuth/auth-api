package dev.jarand.authapi.jarandclient.domain;

import java.time.Instant;
import java.util.UUID;

public class LoginClient extends JarandClient {

    private final String username;
    private final String password;

    public LoginClient(String clientId, String type, String displayName, UUID ownerId, Instant timeOfCreation, String username, String password) {
        super(clientId, type, displayName, ownerId, timeOfCreation);
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
