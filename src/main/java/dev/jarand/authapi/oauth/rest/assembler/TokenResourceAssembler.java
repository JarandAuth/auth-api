package dev.jarand.authapi.oauth.rest.assembler;

import dev.jarand.authapi.oauth.rest.resource.TokenResource;
import org.springframework.stereotype.Component;

@Component
public class TokenResourceAssembler {

    public TokenResource assemble(String accessToken, int accessTokenExpiresIn, String refreshToken) {
        return new TokenResource(accessToken, "bearer", accessTokenExpiresIn, refreshToken);
    }
}
