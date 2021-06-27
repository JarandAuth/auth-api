package dev.jarand.authapi.token.resource;

public class RefreshTokenResource {

    private final String jti;
    private final String subject;
    private final String issuedAt;

    public RefreshTokenResource(String jti, String subject, String issuedAt) {
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

    public String getIssuedAt() {
        return issuedAt;
    }
}
