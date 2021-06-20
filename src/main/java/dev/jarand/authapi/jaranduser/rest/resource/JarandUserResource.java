package dev.jarand.authapi.jaranduser.rest.resource;

import dev.jarand.authapi.jaranduser.jarandclient.rest.resource.JarandClientResource;
import dev.jarand.authapi.jaranduser.jarandclient.rest.resource.LoginClientResource;

import java.util.List;

public class JarandUserResource {

    private final String id;
    private final String email;
    private final String username;
    private final String displayName;
    private final String timeOfCreation;
    private final LoginClientResource loginClient;
    private final List<JarandClientResource> clients;

    public JarandUserResource(String id,
                              String email,
                              String username,
                              String displayName,
                              String timeOfCreation,
                              LoginClientResource loginClient,
                              List<JarandClientResource> clients) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.displayName = displayName;
        this.timeOfCreation = timeOfCreation;
        this.loginClient = loginClient;
        this.clients = clients;
    }

    public String getId() {
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

    public String getTimeOfCreation() {
        return timeOfCreation;
    }

    public LoginClientResource getLoginClient() {
        return loginClient;
    }

    public List<JarandClientResource> getClients() {
        return clients;
    }
}
