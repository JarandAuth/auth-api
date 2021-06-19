package dev.jarand.authapi.oauth.domain;

import dev.jarand.authapi.oauth.validator.OAuthError;

public class RequestParameters {

    private final boolean valid;
    private final GrantType grantType;
    private final ClientCredentialsParameters clientCredentialsParameters;
    private final RefreshTokenParameters refreshTokenParameters;
    private final OAuthError error;

    public RequestParameters(ParameterStatus status, GrantType grantType, ClientCredentialsParameters clientCredentialsParameters, RefreshTokenParameters refreshTokenParameters, OAuthError error) {
        this.valid = ParameterStatus.VALID == status;
        this.grantType = grantType;
        this.clientCredentialsParameters = clientCredentialsParameters;
        this.refreshTokenParameters = refreshTokenParameters;
        this.error = error;
    }

    public boolean isValid() {
        return valid;
    }

    public GrantType getGrantType() {
        return grantType;
    }

    public ClientCredentialsParameters getClientCredentialsParameters() {
        return clientCredentialsParameters;
    }

    public RefreshTokenParameters getRefreshTokenParameters() {
        return refreshTokenParameters;
    }

    public OAuthError getError() {
        return error;
    }
}
