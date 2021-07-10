package dev.jarand.authapi.oauth.rest.assembler;

import dev.jarand.authapi.jarandclient.rest.assembler.JarandClientResourceAssembler;
import dev.jarand.authapi.oauth.domain.AuthorizeCheckResult;
import dev.jarand.authapi.oauth.rest.resource.AuthorizeCheckResultResource;
import dev.jarand.authapi.scope.assembler.ScopeResourceAssembler;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthorizeCheckResultResourceAssembler {

    private final JarandClientResourceAssembler clientResourceAssembler;
    private final ScopeResourceAssembler scopeResourceAssembler;

    public AuthorizeCheckResultResourceAssembler(JarandClientResourceAssembler clientResourceAssembler, ScopeResourceAssembler scopeResourceAssembler) {
        this.clientResourceAssembler = clientResourceAssembler;
        this.scopeResourceAssembler = scopeResourceAssembler;
    }

    public AuthorizeCheckResultResource assemble(AuthorizeCheckResult authorizeCheckResult) {
        return new AuthorizeCheckResultResource(
                Optional.ofNullable(authorizeCheckResult.getErrors()).orElse(null),
                Optional.ofNullable(authorizeCheckResult.getUser()).map(clientResourceAssembler::assemble).orElse(null),
                Optional.ofNullable(authorizeCheckResult.getClient()).map(clientResourceAssembler::assemble).orElse(null),
                Optional.ofNullable(authorizeCheckResult.getScopes()).map(scopeResourceAssembler::assemble).orElse(null),
                Optional.ofNullable(authorizeCheckResult.getClientOwner()).orElse(null));
    }
}
