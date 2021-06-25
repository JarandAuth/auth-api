package dev.jarand.authapi.jaranduser.jarandclient.domain;

import java.time.Instant;
import java.util.UUID;

public class LoginClient extends JarandClient {

    private final String username;
    private final String password;

    public LoginClient(String clientId, String type, UUID ownerId, Instant timeOfCreation, String username, String password) {
        super(clientId, type, ownerId, timeOfCreation);
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
