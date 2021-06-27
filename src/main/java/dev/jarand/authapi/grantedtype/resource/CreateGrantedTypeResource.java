package dev.jarand.authapi.grantedtype.resource;

import javax.validation.constraints.NotNull;

public class CreateGrantedTypeResource {

    private final String grantType;
    private final String clientId;

    public CreateGrantedTypeResource(String grantType, String clientId) {
        this.grantType = grantType;
        this.clientId = clientId;
    }

    @NotNull
    public String getGrantType() {
        return grantType;
    }

    @NotNull
    public String getClientId() {
        return clientId;
    }
}
