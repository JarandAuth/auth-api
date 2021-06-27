package dev.jarand.authapi.granttype.domain;

import java.time.Instant;

public class GrantType {

    private final String grantType;
    private final Instant timeOfCreation;

    public GrantType(String grantType, Instant timeOfCreation) {
        this.grantType = grantType;
        this.timeOfCreation = timeOfCreation;
    }

    public String getGrantType() {
        return grantType;
    }

    public Instant getTimeOfCreation() {
        return timeOfCreation;
    }
}
