package dev.jarand.authapi.grantedtype.domain;

import java.util.UUID;

public class GrantedType {

    private final String grantType;
    private final UUID jarandClientId;

    public GrantedType(String grantType, UUID jarandClientId) {
        this.grantType = grantType;
        this.jarandClientId = jarandClientId;
    }

    public String getGrantType() {
        return grantType;
    }

    public UUID getJarandClientId() {
        return jarandClientId;
    }
}
