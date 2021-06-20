package dev.jarand.authapi.grantedtype.domain;

public class GrantedType {

    private final String grantType;
    private final String jarandClientId;

    public GrantedType(String grantType, String jarandClientId) {
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
