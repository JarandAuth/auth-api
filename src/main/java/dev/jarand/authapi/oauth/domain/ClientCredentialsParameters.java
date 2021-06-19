package dev.jarand.authapi.oauth.domain;

import java.util.Optional;

public class ClientCredentialsParameters {

    private final String clientId;
    private final String clientSecret;
    private final String scope;

    public ClientCredentialsParameters(String clientId, String clientSecret, String scope) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.scope = scope;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public Optional<String> getScope() {
        return Optional.ofNullable(scope);
    }
}
