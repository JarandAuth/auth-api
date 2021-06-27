package dev.jarand.authapi.tokendata.domain;

import java.util.List;

public class TokenData {

    private final String subject;
    private final List<String> scopes;

    public TokenData(String subject, List<String> scopes) {
        this.subject = subject;
        this.scopes = scopes;
    }

    public String getSubject() {
        return subject;
    }

    public List<String> getScopes() {
        return scopes;
    }
}
