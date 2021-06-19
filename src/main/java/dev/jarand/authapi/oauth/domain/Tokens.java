package dev.jarand.authapi.oauth.domain;

public class Tokens {

    private final String accessToken;
    private final int accessTokenExpiresIn;
    private final String refreshToken;

    public Tokens(String accessToken, int accessTokenExpiresIn, String refreshToken) {
        this.accessToken = accessToken;
        this.accessTokenExpiresIn = accessTokenExpiresIn;
        this.refreshToken = refreshToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public int getAccessTokenExpiresIn() {
        return accessTokenExpiresIn;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}
