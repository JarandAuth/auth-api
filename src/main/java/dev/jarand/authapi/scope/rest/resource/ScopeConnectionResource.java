package dev.jarand.authapi.scope.rest.resource;

public class ScopeConnectionResource {

    private final String scopeId;
    private final String jarandClientId;

    public ScopeConnectionResource(String scopeId, String jarandClientId) {
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
