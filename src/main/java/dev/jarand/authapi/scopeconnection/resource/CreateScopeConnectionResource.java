package dev.jarand.authapi.scopeconnection.resource;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

public class CreateScopeConnectionResource {

    private final String scopeId;
    private final String clientId;

    public CreateScopeConnectionResource(@JsonProperty("scopeId") String scopeId, @JsonProperty("clientId") String clientId) {
        this.scopeId = scopeId;
        this.clientId = clientId;
    }

    @NotNull
    public String getScopeId() {
        return scopeId;
    }

    @NotNull
    public String getClientId() {
        return clientId;
    }
}
