package dev.jarand.authapi.scope.rest.resource;

import javax.validation.constraints.NotNull;

public class CreateScopeResource {

    private final String id;
    private final String description;

    public CreateScopeResource(String id, String description) {
        this.id = id;
        this.description = description;
    }

    @NotNull
    public String getId() {
        return id;
    }

    @NotNull
    public String getDescription() {
        return description;
    }
}
