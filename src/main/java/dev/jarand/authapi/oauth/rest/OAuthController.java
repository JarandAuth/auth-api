package dev.jarand.authapi.oauth.rest;

import dev.jarand.authapi.oauth.AuthorizeService;
import dev.jarand.authapi.oauth.ClientCredentialsService;
import dev.jarand.authapi.oauth.PasswordService;
import dev.jarand.authapi.oauth.RefreshTokenService;
import dev.jarand.authapi.oauth.domain.Tokens;
import dev.jarand.authapi.oauth.rest.assembler.AuthorizeCheckResultResourceAssembler;
import dev.jarand.authapi.oauth.rest.assembler.OAuthErrorResourceAssembler;
import dev.jarand.authapi.oauth.rest.assembler.TokenResourceAssembler;
import dev.jarand.authapi.oauth.rest.resource.AuthorizeCheckRequest;
import dev.jarand.authapi.oauth.rest.resource.AuthorizeCheckResultResource;
import dev.jarand.authapi.oauth.validator.RequestParametersValidator;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;
import java.util.Optional;

import static dev.jarand.authapi.oauth.domain.GrantTypeParameter.*;

@RestController
@RequestMapping("oauth")
public class OAuthController {

    private final RequestParametersValidator requestParametersValidator;
    private final OAuthErrorResourceAssembler oauthErrorResourceAssembler;
    private final ClientCredentialsService clientCredentialsService;
    private final RefreshTokenService refreshTokenService;
    private final PasswordService passwordService;
    private final TokenResourceAssembler tokenResourceAssembler;
    private final AuthorizeService authorizeService;
    private final AuthorizeCheckResultResourceAssembler authorizeCheckResultResourceAssembler;

    public OAuthController(RequestParametersValidator requestParametersValidator,
                           OAuthErrorResourceAssembler oauthErrorResourceAssembler,
                           ClientCredentialsService clientCredentialsService,
                           RefreshTokenService refreshTokenService,
                           PasswordService passwordService,
                           TokenResourceAssembler tokenResourceAssembler,
                           AuthorizeService authorizeService,
                           AuthorizeCheckResultResourceAssembler authorizeCheckResultResourceAssembler) {
        this.requestParametersValidator = requestParametersValidator;
        this.oauthErrorResourceAssembler = oauthErrorResourceAssembler;
        this.clientCredentialsService = clientCredentialsService;
        this.refreshTokenService = refreshTokenService;
        this.passwordService = passwordService;
        this.tokenResourceAssembler = tokenResourceAssembler;
        this.authorizeService = authorizeService;
        this.authorizeCheckResultResourceAssembler = authorizeCheckResultResourceAssembler;
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

    @GetMapping("authorize")
    public ResponseEntity<Void> authorize(@RequestParam("response_type") String responseType,
                                          @RequestParam("client_id") String clientId,
                                          @RequestParam("redirect_uri") String redirectUri,
                                          @RequestParam("scope") String scope,
                                          @RequestParam("state") String state) {
        final var authorizeCheckResult = authorizeService.validate(responseType, clientId, redirectUri, scope, state);
        if (!authorizeCheckResult.getErrors().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(HttpStatus.FOUND).header(HttpHeaders.LOCATION, "http://localhost:8082/storage/archive/v0/oauth/callback?code=" + "someCode").build();
    }

    @PostMapping("authorize/check")
    public ResponseEntity<AuthorizeCheckResultResource> checkAuthorizeRequest(@Valid @RequestBody AuthorizeCheckRequest request) {
        final var authorizeCheckResult = authorizeService.validate(request);
        final var authorizeCheckResultResource = authorizeCheckResultResourceAssembler.assemble(authorizeCheckResult);
        if (!authorizeCheckResult.getErrors().isEmpty()) {
            return ResponseEntity.badRequest().body(authorizeCheckResultResource);
        }
        return ResponseEntity.ok(authorizeCheckResultResource);
    }
}
