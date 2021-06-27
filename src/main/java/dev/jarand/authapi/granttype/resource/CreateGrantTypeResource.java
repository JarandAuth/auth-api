package dev.jarand.authapi.granttype.resource;

import javax.validation.constraints.NotNull;

public class CreateGrantTypeResource {

    private final String grantType;

    public CreateGrantTypeResource(String grantType) {
        this.grantType = grantType;
    }

    @NotNull
    public String getGrantType() {
        return grantType;
    }
}
