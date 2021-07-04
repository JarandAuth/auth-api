package dev.jarand.authapi.oauth.domain;

import java.util.Optional;

public class RefreshTokenParameters {

    private final String clientId;
    private final String clientSecret;
    private final String refreshToken;

    public RefreshTokenParameters(String clientId, String clientSecret, String refreshToken) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.refreshToken = refreshToken;
    }

    public Optional<String> getClientId() {
        return Optional.ofNullable(clientId);
    }

    public Optional<String> getClientSecret() {
        return Optional.ofNullable(clientSecret);
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}
