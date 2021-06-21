package dev.jarand.authapi.scope.rest.resource;

public class ScopeConnectionResource {

    private final String scopeId;
    private final String clientId;

    public ScopeConnectionResource(String scopeId, String clientId) {
        this.scopeId = scopeId;
        this.clientId = clientId;
    }

    public String getScopeId() {
        return scopeId;
    }

    public String getClientId() {
        return clientId;
    }
}
