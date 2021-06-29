package dev.jarand.authapi.scopeconnection.resource;

public class ScopeConnectionResource {

    private final String scopeId;
    private final String clientId;
    private final String timeOfCreation;

    public ScopeConnectionResource(String scopeId, String clientId, String timeOfCreation) {
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

    public String getTimeOfCreation() {
        return timeOfCreation;
    }
}
