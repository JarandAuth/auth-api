package dev.jarand.authapi.scope.rest.resource;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

public class CreateScopeConnectionResource {

    private final String clientId;

    public CreateScopeConnectionResource(@JsonProperty("clientId") String clientId) {
        this.clientId = clientId;
    }

    @NotNull
    public String getClientId() {
        return clientId;
    }
}
