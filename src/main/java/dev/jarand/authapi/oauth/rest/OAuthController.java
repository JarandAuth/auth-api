package dev.jarand.authapi.oauth.rest;

import dev.jarand.authapi.oauth.rest.assembler.OAuthErrorResourceAssembler;
import dev.jarand.authapi.oauth.rest.resource.TokenResourceAssembler;
import dev.jarand.authapi.oauth.service.ClientCredentialsService;
import dev.jarand.authapi.oauth.service.RefreshTokenService;
import dev.jarand.authapi.oauth.validator.RequestParametersAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static dev.jarand.authapi.oauth.domain.GrantType.CLIENT_CREDENTIALS;
import static dev.jarand.authapi.oauth.domain.GrantType.REFRESH_TOKEN;

@RestController
@RequestMapping("oauth")
public class OAuthController {

    private final RequestParametersAssembler requestParametersAssembler;
    private final OAuthErrorResourceAssembler oauthErrorResourceAssembler;
    private final ClientCredentialsService clientCredentialsService;
    private final RefreshTokenService refreshTokenService;
    private final TokenResourceAssembler tokenResourceAssembler;

    public OAuthController(RequestParametersAssembler requestParametersAssembler,
                           OAuthErrorResourceAssembler oauthErrorResourceAssembler,
                           ClientCredentialsService clientCredentialsService,
                           RefreshTokenService refreshTokenService,
                           TokenResourceAssembler tokenResourceAssembler) {
        this.requestParametersAssembler = requestParametersAssembler;
        this.oauthErrorResourceAssembler = oauthErrorResourceAssembler;
        this.clientCredentialsService = clientCredentialsService;
        this.refreshTokenService = refreshTokenService;
        this.tokenResourceAssembler = tokenResourceAssembler;
    }

    @PostMapping("token")
    public ResponseEntity<?> exchangeToken(@RequestParam Map<String, String> uncheckedRequestParameters) {
        final var requestParameters = requestParametersAssembler.assemble(uncheckedRequestParameters);
        if (!requestParameters.isValid()) {
            return ResponseEntity.badRequest().body(oauthErrorResourceAssembler.assemble(requestParameters.getError()));
        }
        if (requestParameters.getGrantType() == CLIENT_CREDENTIALS) {
            final var optionalTokens = clientCredentialsService.handle(requestParameters.getClientCredentialsParameters());
            if (optionalTokens.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            final var tokens = optionalTokens.get();
            return ResponseEntity.ok(tokenResourceAssembler.assemble(tokens.getAccessToken(), tokens.getAccessTokenExpiresIn(), tokens.getRefreshToken()));
        } else if (requestParameters.getGrantType() == REFRESH_TOKEN) {
            final var optionalTokens = refreshTokenService.handle(requestParameters.getRefreshTokenParameters());
            if (optionalTokens.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            final var tokens = optionalTokens.get();
            return ResponseEntity.ok(tokenResourceAssembler.assemble(tokens.getAccessToken(), tokens.getAccessTokenExpiresIn(), tokens.getRefreshToken()));
        }
        throw new IllegalStateException("Unexpected grantType: " + requestParameters.getGrantType().name());
    }
}
