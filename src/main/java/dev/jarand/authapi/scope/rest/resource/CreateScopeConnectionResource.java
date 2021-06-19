package dev.jarand.authapi.scope.rest.resource;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

public class CreateScopeConnectionResource {

    private final String jarandClientId;

    public CreateScopeConnectionResource(@JsonProperty("jarandClientId") String jarandClientId) {
        this.jarandClientId = jarandClientId;
    }

    @NotNull
    public String getJarandClientId() {
        return jarandClientId;
    }
}
