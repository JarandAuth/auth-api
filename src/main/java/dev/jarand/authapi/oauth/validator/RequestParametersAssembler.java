package dev.jarand.authapi.oauth.validator;

import dev.jarand.authapi.oauth.domain.ClientCredentialsParameters;
import dev.jarand.authapi.oauth.domain.RefreshTokenParameters;
import dev.jarand.authapi.oauth.domain.RequestParameters;
import org.springframework.stereotype.Component;

import java.util.Map;

import static dev.jarand.authapi.oauth.domain.GrantType.*;
import static dev.jarand.authapi.oauth.domain.ParameterStatus.INVALID;
import static dev.jarand.authapi.oauth.domain.ParameterStatus.VALID;

@Component
public class RequestParametersAssembler {

    public RequestParameters assemble(Map<String, String> requestParams) {
        final var grantType = requestParams.get("grant_type");
        if (grantType == null) {
            return new RequestParameters(
                    INVALID,
                    UNKNOWN,
                    null,
                    null,
                    new OAuthError("unsupported_grant_type", "Missing 'grant_type' parameter"));
        }
        if ("client_credentials".equals(grantType)) {
            final var clientId = requestParams.get("client_id");
            final var clientSecret = requestParams.get("client_secret");
            if (clientId == null || clientSecret == null) {
                return new RequestParameters(
                        INVALID,
                        CLIENT_CREDENTIALS,
                        null,
                        null,
                        new OAuthError("invalid_request", "Missing 'client_id' or 'client_secret' parameter required for the 'client_credentials' grant type"));
            }
            return new RequestParameters(
                    VALID,
                    CLIENT_CREDENTIALS,
                    new ClientCredentialsParameters(clientId, clientSecret),
                    null,
                    null);
        } else if ("refresh_token".equals(grantType)) {
            final var clientId = requestParams.get("client_id");
            final var clientSecret = requestParams.get("client_secret");
            final var refreshToken = requestParams.get("refresh_token");
            if (clientId == null || clientSecret == null || refreshToken == null) {
                return new RequestParameters(
                        INVALID,
                        REFRESH_TOKEN,
                        null,
                        null,
                        new OAuthError("invalid_request", "Missing 'client_id' or 'client_secret' or 'refresh_token' parameter required for the 'refresh_token' grant type"));
            }
            return new RequestParameters(
                    VALID,
                    REFRESH_TOKEN,
                    null,
                    new RefreshTokenParameters(clientId, clientSecret, refreshToken),
                    null);
        } else {
            return new RequestParameters(
                    INVALID,
                    UNKNOWN,
                    null,
                    null,
                    new OAuthError("unsupported_grant_type", "The specified grant type is not supported, 'client_credentials' or 'refresh_token' expected"));
        }
    }
}
