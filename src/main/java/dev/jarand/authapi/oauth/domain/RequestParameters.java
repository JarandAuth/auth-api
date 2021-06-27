package dev.jarand.authapi.oauth.domain;

import dev.jarand.authapi.oauth.validator.OAuthError;

public class RequestParameters {

    private final boolean valid;
    private final GrantTypeParameter grantTypeParameter;
    private final ClientCredentialsParameters clientCredentialsParameters;
    private final RefreshTokenParameters refreshTokenParameters;
    private final PasswordParameters passwordParameters;
    private final OAuthError error;

    public RequestParameters(ParameterStatus status,
                             GrantTypeParameter grantTypeParameter,
                             ClientCredentialsParameters clientCredentialsParameters,
                             RefreshTokenParameters refreshTokenParameters,
                             PasswordParameters passwordParameters,
                             OAuthError error) {
        this.valid = ParameterStatus.VALID == status;
        this.grantTypeParameter = grantTypeParameter;
        this.clientCredentialsParameters = clientCredentialsParameters;
        this.refreshTokenParameters = refreshTokenParameters;
        this.passwordParameters = passwordParameters;
        this.error = error;
    }

    public static RequestParameters clientCredentials(ParameterStatus status, ClientCredentialsParameters parameters, OAuthError error) {
        return new RequestParameters(status, GrantTypeParameter.CLIENT_CREDENTIALS, parameters, null, null, error);
    }

    public static RequestParameters refreshToken(ParameterStatus status, RefreshTokenParameters parameters, OAuthError error) {
        return new RequestParameters(status, GrantTypeParameter.REFRESH_TOKEN, null, parameters, null, error);
    }

    public static RequestParameters password(ParameterStatus status, PasswordParameters parameters, OAuthError error) {
        return new RequestParameters(status, GrantTypeParameter.PASSWORD, null, null, parameters, error);
    }

    public static RequestParameters unknown(ParameterStatus status, OAuthError error) {
        return new RequestParameters(status, GrantTypeParameter.UNKNOWN, null, null, null, error);
    }

    public boolean isValid() {
        return valid;
    }

    public GrantTypeParameter getGrantType() {
        return grantTypeParameter;
    }

    public ClientCredentialsParameters getClientCredentialsParameters() {
        return clientCredentialsParameters;
    }

    public RefreshTokenParameters getRefreshTokenParameters() {
        return refreshTokenParameters;
    }

    public PasswordParameters getPasswordParameters() {
        return passwordParameters;
    }

    public OAuthError getError() {
        return error;
    }
}
