package dev.jarand.authapi.oauth.domain;

public class RefreshTokenParameters {

    private final String clientId;
    private final String clientSecret;
    private final String refreshToken;

    public RefreshTokenParameters(String clientId, String clientSecret, String refreshToken) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.refreshToken = refreshToken;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}
