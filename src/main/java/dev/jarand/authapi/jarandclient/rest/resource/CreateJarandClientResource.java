package dev.jarand.authapi.jarandclient.rest.resource;

public class CreateJarandClientResource {

    private final String clientId;
    private final String clientSecret;

    public CreateJarandClientResource(String clientId, String clientSecret) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }
}
