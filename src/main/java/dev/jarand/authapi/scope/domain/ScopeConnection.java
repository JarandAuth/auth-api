package dev.jarand.authapi.scope.domain;

import java.util.UUID;

public class ScopeConnection {

    private final String scopeId;
    private final UUID jarandClientId;

    public ScopeConnection(String scopeId, UUID jarandClientId) {
        this.scopeId = scopeId;
        this.jarandClientId = jarandClientId;
    }

    public String getScopeId() {
        return scopeId;
    }

    public UUID getJarandClientId() {
        return jarandClientId;
    }
}
