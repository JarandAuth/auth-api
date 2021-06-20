package dev.jarand.authapi.grantedtype.resource;

public class GrantedTypeResource {

    private final String grantType;
    private final String clientId;

    public GrantedTypeResource(String grantType, String clientId) {
        this.grantType = grantType;
        this.clientId = clientId;
    }

    public String getGrantType() {
        return grantType;
    }

    public String getClientId() {
        return clientId;
    }
}
