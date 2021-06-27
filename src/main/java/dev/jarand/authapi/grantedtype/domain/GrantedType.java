package dev.jarand.authapi.grantedtype.domain;

import java.time.Instant;

public class GrantedType {

    private final String grantType;
    private final String clientId;
    private final Instant timeOfCreation;

    public GrantedType(String grantType, String clientId, Instant timeOfCreation) {
        this.grantType = grantType;
        this.clientId = clientId;
        this.timeOfCreation = timeOfCreation;
    }

    public String getGrantType() {
        return grantType;
    }

    public String getClientId() {
        return clientId;
    }

    public Instant getTimeOfCreation() {
        return timeOfCreation;
    }
}
