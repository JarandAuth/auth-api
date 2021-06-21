package dev.jarand.authapi.scope.domain;

public class ScopeConnection {

    private final String scopeId;
    private final String clientId;

    public ScopeConnection(String scopeId, String clientId) {
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
