package dev.jarand.authapi.token.domain;

import java.util.Optional;

public class RefreshTokenClaims {

    private final String jti;
    private final String subject;
    private final String scope;

    public RefreshTokenClaims(String jti, String subject, String scope) {
        this.jti = jti;
        this.subject = subject;
        this.scope = scope;
    }

    public String getJti() {
        return jti;
    }

    public String getSubject() {
        return subject;
    }

    public Optional<String> getScope() {
        return Optional.ofNullable(scope);
    }
}
