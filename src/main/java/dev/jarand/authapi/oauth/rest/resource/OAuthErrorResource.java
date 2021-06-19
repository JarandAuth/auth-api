package dev.jarand.authapi.oauth.rest.resource;

import com.fasterxml.jackson.annotation.JsonGetter;

public class OAuthErrorResource {

    private final String error;
    private final String errorDescription;

    public OAuthErrorResource(String error, String errorDescription) {
        this.error = error;
        this.errorDescription = errorDescription;
    }

    @JsonGetter("error")
    public String getError() {
        return error;
    }

    @JsonGetter("error_description")
    public String getErrorDescription() {
        return errorDescription;
    }
}
