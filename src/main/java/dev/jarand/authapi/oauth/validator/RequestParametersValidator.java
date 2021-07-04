package dev.jarand.authapi.oauth.validator;

import dev.jarand.authapi.oauth.domain.ClientCredentialsParameters;
import dev.jarand.authapi.oauth.domain.PasswordParameters;
import dev.jarand.authapi.oauth.domain.RefreshTokenParameters;
import dev.jarand.authapi.oauth.domain.RequestParameters;
import org.springframework.stereotype.Component;

import java.util.Map;

import static dev.jarand.authapi.oauth.domain.ParameterStatus.INVALID;
import static dev.jarand.authapi.oauth.domain.ParameterStatus.VALID;

@Component
public class RequestParametersValidator {

    public RequestParameters validate(Map<String, String> requestParams) {
        final var grantType = requestParams.get("grant_type");
        if (grantType == null) {
            return RequestParameters.unknown(INVALID, new OAuthError("unsupported_grant_type", "Missing 'grant_type' parameter"));
        }
        switch (grantType) {
            case "client_credentials": {
                final var clientId = requestParams.get("client_id");
                final var clientSecret = requestParams.get("client_secret");
                if (clientId == null || clientSecret == null) {
                    return RequestParameters.clientCredentials(
                            INVALID,
                            null,
                            new OAuthError("invalid_request", "Missing 'client_id' or 'client_secret' parameter required for the 'client_credentials' grant type"));
                }
                return RequestParameters.clientCredentials(VALID, new ClientCredentialsParameters(clientId, clientSecret, requestParams.get("scope")), null);
            }
            case "refresh_token": {
                final var clientId = requestParams.get("client_id");
                final var clientSecret = requestParams.get("client_secret");
                final var refreshToken = requestParams.get("refresh_token");
                if (refreshToken == null) {
                    return RequestParameters.refreshToken(
                            INVALID,
                            null,
                            new OAuthError("invalid_request", "Missing 'refresh_token' parameter required for the 'refresh_token' grant type"));
                }
                return RequestParameters.refreshToken(VALID, new RefreshTokenParameters(clientId, clientSecret, refreshToken), null);
            }
            case "password":
                final var username = requestParams.get("username");
                final var password = requestParams.get("password");
                if (username == null || password == null) {
                    return RequestParameters.password(
                            INVALID,
                            null,
                            new OAuthError("invalid_request", "Missing 'username' or 'password' parameter required for the 'password' grant type"));
                }
                return RequestParameters.password(VALID, new PasswordParameters(username, password, requestParams.get("scope")), null);
            default:
                return RequestParameters.unknown(
                        INVALID,
                        new OAuthError("unsupported_grant_type", "The specified grant type is not supported, 'client_credentials' or 'refresh_token' expected"));
        }
    }
}
