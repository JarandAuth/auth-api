package dev.jarand.authapi.oauth.rest.assembler;

import dev.jarand.authapi.oauth.rest.resource.OAuthErrorResource;
import dev.jarand.authapi.oauth.validator.OAuthError;
import org.springframework.stereotype.Component;

@Component
public class OAuthErrorResourceAssembler {

    public OAuthErrorResource assemble(OAuthError error) {
        return new OAuthErrorResource(error.getError(), error.getErrorDescription());
    }
}
