package dev.jarand.authapi.token.assembler;

import dev.jarand.authapi.token.domain.RefreshToken;
import dev.jarand.authapi.token.resource.RefreshTokenResource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RefreshTokenResourceAssembler {

    public List<RefreshTokenResource> assemble(List<RefreshToken> refreshTokens) {
        return refreshTokens.stream().map(this::assemble).toList();
    }

    private RefreshTokenResource assemble(RefreshToken refreshToken) {
        return new RefreshTokenResource(refreshToken.getJti(), refreshToken.getSubject(), refreshToken.getIssuedAt().toString());
    }
}
