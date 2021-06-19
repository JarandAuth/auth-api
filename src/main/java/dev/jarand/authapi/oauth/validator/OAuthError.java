package dev.jarand.authapi.oauth.validator;

public class OAuthError {

    private final String error;
    private final String errorDescription;

    public OAuthError(String error, String errorDescription) {
        this.error = error;
        this.errorDescription = errorDescription;
    }

    public String getError() {
        return error;
    }

    public String getErrorDescription() {
        return errorDescription;
    }
}
