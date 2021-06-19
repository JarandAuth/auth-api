package dev.jarand.authapi.jarandclient.rest.resource;

public class CreateJarandClientResource {

    private final String clientId;
    private final String clientSecret;
    private final String ownerId;

    public CreateJarandClientResource(String clientId, String clientSecret, String ownerId) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.ownerId = ownerId;
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
}
