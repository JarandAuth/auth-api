package dev.jarand.authapi.tokendata.assembler;

import dev.jarand.authapi.tokendata.domain.TokenData;
import dev.jarand.authapi.tokendata.resource.TokenDataResource;
import org.springframework.stereotype.Component;

@Component
public class TokenDataResourceAssembler {

    public TokenDataResource assemble(TokenData tokenData) {
        return new TokenDataResource(tokenData.getSubject(), tokenData.getScopes());
    }
}
