package dev.jarand.authapi.oauth.rest;

import dev.jarand.authapi.oauth.ClientCredentialsService;
import dev.jarand.authapi.oauth.PasswordService;
import dev.jarand.authapi.oauth.RefreshTokenService;
import dev.jarand.authapi.oauth.domain.Tokens;
import dev.jarand.authapi.oauth.rest.assembler.OAuthErrorResourceAssembler;
import dev.jarand.authapi.oauth.rest.resource.TokenResourceAssembler;
import dev.jarand.authapi.oauth.validator.RequestParametersValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

import static dev.jarand.authapi.oauth.domain.GrantType.*;

@RestController
@RequestMapping("oauth")
public class OAuthController {

    private final RequestParametersValidator requestParametersValidator;
    private final OAuthErrorResourceAssembler oauthErrorResourceAssembler;
    private final ClientCredentialsService clientCredentialsService;
    private final RefreshTokenService refreshTokenService;
    private final PasswordService passwordService;
    private final TokenResourceAssembler tokenResourceAssembler;

    public OAuthController(RequestParametersValidator requestParametersValidator,
                           OAuthErrorResourceAssembler oauthErrorResourceAssembler,
                           ClientCredentialsService clientCredentialsService,
                           RefreshTokenService refreshTokenService,
                           PasswordService passwordService,
                           TokenResourceAssembler tokenResourceAssembler) {
        this.requestParametersValidator = requestParametersValidator;
        this.oauthErrorResourceAssembler = oauthErrorResourceAssembler;
        this.clientCredentialsService = clientCredentialsService;
        this.refreshTokenService = refreshTokenService;
        this.passwordService = passwordService;
        this.tokenResourceAssembler = tokenResourceAssembler;
    }

    @PostMapping("token")
    public ResponseEntity<?> exchangeToken(@RequestParam Map<String, String> uncheckedRequestParameters) {
        final var requestParameters = requestParametersValidator.validate(uncheckedRequestParameters);
        if (!requestParameters.isValid()) {
            return ResponseEntity.badRequest().body(oauthErrorResourceAssembler.assemble(requestParameters.getError()));
        }
        Optional<Tokens> optionalTokens = Optional.empty();
        if (requestParameters.getGrantType() == CLIENT_CREDENTIALS) {
            optionalTokens = clientCredentialsService.handle(requestParameters.getClientCredentialsParameters());
        } else if (requestParameters.getGrantType() == REFRESH_TOKEN) {
            optionalTokens = refreshTokenService.handle(requestParameters.getRefreshTokenParameters());
        } else if (requestParameters.getGrantType() == PASSWORD) {
            optionalTokens = passwordService.handle(requestParameters.getPasswordParameters());
        }
        if (optionalTokens.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        final var tokens = optionalTokens.get();
        return ResponseEntity.ok(tokenResourceAssembler.assemble(tokens.getAccessToken(), tokens.getAccessTokenExpiresIn(), tokens.getRefreshToken()));
    }
}
