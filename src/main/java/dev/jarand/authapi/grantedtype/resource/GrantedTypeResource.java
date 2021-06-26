package dev.jarand.authapi.grantedtype.resource;

public class GrantedTypeResource {

    private final String grantType;
    private final String clientId;
    private final String timeOfCreation;

    public GrantedTypeResource(String grantType, String clientId, String timeOfCreation) {
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

    public String getTimeOfCreation() {
        return timeOfCreation;
    }
}
