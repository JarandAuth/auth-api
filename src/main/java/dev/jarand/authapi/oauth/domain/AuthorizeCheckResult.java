package dev.jarand.authapi.oauth.domain;

import dev.jarand.authapi.jarandclient.domain.JarandClient;
import dev.jarand.authapi.scope.domain.Scope;

import java.util.List;

public class AuthorizeCheckResult {

    private final List<String> errors;
    private final JarandClient user;
    private final JarandClient client;
    private final List<Scope> scopes;
    private final String clientOwner;

    public AuthorizeCheckResult(List<String> errors, JarandClient user, JarandClient client, List<Scope> scopes, String clientOwner) {
        this.errors = errors;
        this.user = user;
        this.client = client;
        this.scopes = scopes;
        this.clientOwner = clientOwner;
    }

    public List<String> getErrors() {
        return errors;
    }

    public JarandClient getUser() {
        return user;
    }

    public JarandClient getClient() {
        return client;
    }

    public List<Scope> getScopes() {
        return scopes;
    }

    public String getClientOwner() {
        return clientOwner;
    }
}
