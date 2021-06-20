package dev.jarand.authapi.grantedtype.resource;

public class GrantedTypeResource {

    private final String grantType;
    private final String jarandClientId;

    public GrantedTypeResource(String grantType, String jarandClientId) {
        this.grantType = grantType;
        this.jarandClientId = jarandClientId;
    }

    public String getGrantType() {
        return grantType;
    }

    public String getJarandClientId() {
        return jarandClientId;
    }
}
