package dev.jarand.authapi.jarandclient.rest.resource;

import javax.validation.constraints.NotNull;

public class CreateJarandClientResource {

    private final String displayName;
    private final String clientId;
    private final String clientSecret;

    public CreateJarandClientResource(String displayName, String clientId, String clientSecret) {
        this.displayName = displayName;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    @NotNull
    public String getDisplayName() {
        return displayName;
    }

    @NotNull
    public String getClientId() {
        return clientId;
    }

    @NotNull
    public String getClientSecret() {
        return clientSecret;
    }
}
