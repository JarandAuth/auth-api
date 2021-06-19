package dev.jarand.authapi.oauth.domain;

public class ClientCredentialsParameters {

    private final String clientId;
    private final String clientSecret;

    public ClientCredentialsParameters(String clientId, String clientSecret) {
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
