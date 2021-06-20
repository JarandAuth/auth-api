package dev.jarand.authapi.jaranduser.jarandclient.rest.resource;

import dev.jarand.authapi.grantedtype.resource.GrantedTypeResource;

import java.util.List;

public class JarandClientResource {

    private final String id;
    private final String clientId;
    private final String clientSecret;
    private final String ownerId;
    private final String timeOfCreation;
    private final List<GrantedTypeResource> grantedTypes;

    public JarandClientResource(String id, String clientId, String clientSecret, String ownerId, String timeOfCreation, List<GrantedTypeResource> grantedTypes) {
        this.id = id;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.ownerId = ownerId;
        this.timeOfCreation = timeOfCreation;
        this.grantedTypes = grantedTypes;
    }

    public String getId() {
        return id;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
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
