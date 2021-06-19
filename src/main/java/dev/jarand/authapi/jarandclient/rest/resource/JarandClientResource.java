package dev.jarand.authapi.jarandclient.rest.resource;

public class JarandClientResource {

    private final String id;
    private final String clientId;
    private final String clientSecret;
    private final String ownerId;
    private final String timeOfCreation;

    public JarandClientResource(String id, String clientId, String clientSecret, String ownerId, String timeOfCreation) {
        this.id = id;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.ownerId = ownerId;
        this.timeOfCreation = timeOfCreation;
    }

    public String getId() {
        return id;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public String getTimeOfCreation() {
        return timeOfCreation;
    }
}
