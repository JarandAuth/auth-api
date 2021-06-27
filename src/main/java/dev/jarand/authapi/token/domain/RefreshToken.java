package dev.jarand.authapi.token.domain;

import java.time.Instant;

public class RefreshToken {

    private final String jti;
    private final String subject;
    private final Instant issuedAt;

    public RefreshToken(String jti, String subject, Instant issuedAt) {
        this.jti = jti;
        this.subject = subject;
        this.issuedAt = issuedAt;
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
}
