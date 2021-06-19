package dev.jarand.authapi.oauth.service;

import dev.jarand.authapi.oauth.domain.RefreshTokenParameters;
import dev.jarand.authapi.oauth.domain.Tokens;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RefreshTokenService {

    public Optional<Tokens> handle(RefreshTokenParameters parameters) {
        // TODO: Implement refresh token flow
        return Optional.empty();
    }
}
