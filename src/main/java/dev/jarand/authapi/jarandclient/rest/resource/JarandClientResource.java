package dev.jarand.authapi.jarandclient.rest.resource;

import com.fasterxml.jackson.annotation.JsonInclude;
import dev.jarand.authapi.grantedtype.resource.GrantedTypeResource;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class JarandClientResource {

    private final String clientId;
    private final String ownerId;
    private final String timeOfCreation;
    private final String clientSecret;
    private final String username;
    private final String password;
    private final List<GrantedTypeResource> grantedTypes;

    public JarandClientResource(String clientId,
                                String ownerId,
                                String timeOfCreation,
                                String clientSecret,
                                String username,
                                String password,
                                List<GrantedTypeResource> grantedTypes) {
        this.clientId = clientId;
        this.ownerId = ownerId;
        this.timeOfCreation = timeOfCreation;
        this.clientSecret = clientSecret;
        this.username = username;
        this.password = password;
        this.grantedTypes = grantedTypes;
    }

    public String getClientId() {
        return clientId;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public String getTimeOfCreation() {
        return timeOfCreation;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public List<GrantedTypeResource> getGrantedTypes() {
        return grantedTypes;
    }
}
