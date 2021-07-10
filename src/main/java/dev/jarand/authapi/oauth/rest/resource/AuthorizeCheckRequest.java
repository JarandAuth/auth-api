package dev.jarand.authapi.oauth.rest.resource;

import javax.validation.constraints.NotNull;

public class AuthorizeCheckRequest {

    private final String responseType;
    private final String clientId;
    private final String redirectUri;
    private final String scope;
    private final String state;

    public AuthorizeCheckRequest(String responseType, String clientId, String redirectUri, String scope, String state) {
        this.responseType = responseType;
        this.clientId = clientId;
        this.redirectUri = redirectUri;
        this.scope = scope;
        this.state = state;
    }

    @NotNull
    public String getResponseType() {
        return responseType;
    }

    @NotNull
    public String getClientId() {
        return clientId;
    }

    @NotNull
    public String getRedirectUri() {
        return redirectUri;
    }

    @NotNull
    public String getScope() {
        return scope;
    }

    @NotNull
    public String getState() {
        return state;
    }
}
