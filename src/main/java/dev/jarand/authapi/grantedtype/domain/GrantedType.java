package dev.jarand.authapi.grantedtype.domain;

public class GrantedType {

    private final String grantType;
    private final String clientId;

    public GrantedType(String grantType, String clientId) {
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
