package dev.jarand.authapi.oauth.service;

import dev.jarand.authapi.oauth.domain.PasswordParameters;
import dev.jarand.authapi.oauth.domain.Tokens;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PasswordService {

    public Optional<Tokens> handle(PasswordParameters parameters) {
        // TODO: Implement
        return Optional.empty();
    }
}
