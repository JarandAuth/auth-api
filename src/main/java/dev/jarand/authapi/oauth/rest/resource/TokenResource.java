package dev.jarand.authapi.oauth.rest.resource;

import com.fasterxml.jackson.annotation.JsonGetter;

public class TokenResource {

    private final String accessToken;
    private final String tokenType;
    private final Integer expiresIn;
    private final String refreshToken;

    public TokenResource(String accessToken, String tokenType, Integer expiresIn, String refreshToken) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.expiresIn = expiresIn;
        this.refreshToken = refreshToken;
    }

    @JsonGetter("access_token")
    public String getAccessToken() {
        return accessToken;
    }

    @JsonGetter("token_type")
    public String getTokenType() {
        return tokenType;
    }

    @JsonGetter("expires_in")
    public Integer getExpiresIn() {
        return expiresIn;
    }

    @JsonGetter("refresh_token")
    public String getRefreshToken() {
        return refreshToken;
    }
}
