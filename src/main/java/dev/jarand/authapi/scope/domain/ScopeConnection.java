package dev.jarand.authapi.scope.domain;

public class ScopeConnection {

    private final String scopeId;
    private final String jarandClientId;

    public ScopeConnection(String scopeId, String jarandClientId) {
        this.scopeId = scopeId;
        this.jarandClientId = jarandClientId;
    }

    public String getScopeId() {
        return scopeId;
    }

    public String getJarandClientId() {
        return jarandClientId;
    }
}
