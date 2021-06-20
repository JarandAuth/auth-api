package dev.jarand.authapi.oauth.domain;

import java.time.Instant;
import java.util.Optional;

public class RefreshTokenClaims {

    private final String jti;
    private final String subject;
    private final Instant issuedAt;
    private final Instant expiration;
    private final String scope;

    public RefreshTokenClaims(String jti, String subject, Instant issuedAt, Instant expiration, String scope) {
        this.jti = jti;
        this.subject = subject;
        this.issuedAt = issuedAt;
        this.expiration = expiration;
        this.scope = scope;
    }

    public String getJti() {
        return jti;
    }

    public String getSubject() {
        return subject;
    }

    public Instant getIssuedAt() {
        return issuedAt;
    }

    public Instant getExpiration() {
        return expiration;
    }

    public Optional<String> getScope() {
        return Optional.ofNullable(scope);
    }
}
