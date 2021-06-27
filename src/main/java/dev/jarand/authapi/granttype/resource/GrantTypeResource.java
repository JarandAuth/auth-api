package dev.jarand.authapi.granttype.resource;

public class GrantTypeResource {

    private final String grantType;
    private final String timeOfCreation;

    public GrantTypeResource(String grantType, String timeOfCreation) {
        this.grantType = grantType;
        this.timeOfCreation = timeOfCreation;
    }

    public String getGrantType() {
        return grantType;
    }

    public String getTimeOfCreation() {
        return timeOfCreation;
    }
}
