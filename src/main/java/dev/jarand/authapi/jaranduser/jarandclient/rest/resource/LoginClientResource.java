package dev.jarand.authapi.jaranduser.jarandclient.rest.resource;

import dev.jarand.authapi.grantedtype.resource.GrantedTypeResource;

import java.util.List;

public class LoginClientResource {

    private final String clientId;
    private final String username;
    private final String password;
    private final String ownerId;
    private final String timeOfCreation;
    private final List<GrantedTypeResource> grantedTypes;

    public LoginClientResource(String clientId, String username, String password, String ownerId, String timeOfCreation, List<GrantedTypeResource> grantedTypes) {
        this.clientId = clientId;
        this.username = username;
        this.password = password;
        this.ownerId = ownerId;
        this.timeOfCreation = timeOfCreation;
        this.grantedTypes = grantedTypes;
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

    public String getOwnerId() {
        return ownerId;
    }

    public String getTimeOfCreation() {
        return timeOfCreation;
    }

    public List<GrantedTypeResource> getGrantedTypes() {
        return grantedTypes;
    }
}
