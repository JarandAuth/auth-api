package dev.jarand.authapi.scope.domain;

import java.time.Instant;

public class ScopeConnection {

    private final String scopeId;
    private final String clientId;
    private final Instant timeOfCreation;

    public ScopeConnection(String scopeId, String clientId, Instant timeOfCreation) {
        this.scopeId = scopeId;
        this.clientId = clientId;
        this.timeOfCreation = timeOfCreation;
    }

    public String getScopeId() {
        return scopeId;
    }

    public String getClientId() {
        return clientId;
    }

    public Instant getTimeOfCreation() {
        return timeOfCreation;
    }
}
