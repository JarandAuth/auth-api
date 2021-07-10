package dev.jarand.authapi.oauth.rest.resource;

import dev.jarand.authapi.jarandclient.rest.resource.JarandClientResource;
import dev.jarand.authapi.scope.resource.ScopeResource;

import java.util.List;

public class AuthorizeCheckResultResource {

    private final List<String> errors;
    private final JarandClientResource user;
    private final JarandClientResource client;
    private final List<ScopeResource> scopes;
    private final String clientOwner;

    public AuthorizeCheckResultResource(List<String> errors, JarandClientResource user, JarandClientResource client, List<ScopeResource> scopes, String clientOwner) {
        this.errors = errors;
        this.user = user;
        this.client = client;
        this.scopes = scopes;
        this.clientOwner = clientOwner;
    }

    public List<String> getErrors() {
        return errors;
    }

    public JarandClientResource getUser() {
        return user;
    }

    public JarandClientResource getClient() {
        return client;
    }

    public List<ScopeResource> getScopes() {
        return scopes;
    }

    public String getClientOwner() {
        return clientOwner;
    }
}
