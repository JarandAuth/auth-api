package dev.jarand.authapi.tokendata.resource;

import java.util.List;

public class TokenDataResource {

    private final String subject;
    private final List<String> scopes;

    public TokenDataResource(String subject, List<String> scopes) {
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
