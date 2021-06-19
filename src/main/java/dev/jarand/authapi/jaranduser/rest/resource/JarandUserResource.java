package dev.jarand.authapi.jaranduser.rest.resource;

import dev.jarand.authapi.jaranduser.jarandclient.rest.resource.JarandClientResource;

import java.util.List;

public class JarandUserResource {

    private final String id;
    private final String email;
    private final String username;
    private final String displayName;
    private final String password;
    private final String timeOfCreation;
    private final List<JarandClientResource> clients;

    public JarandUserResource(String id, String email, String username, String displayName, String password, String timeOfCreation, List<JarandClientResource> clients) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.displayName = displayName;
        this.password = password;
        this.timeOfCreation = timeOfCreation;
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

    public String getPassword() {
        return password;
    }

    public String getTimeOfCreation() {
        return timeOfCreation;
    }

    public List<JarandClientResource> getClients() {
        return clients;
    }
}
