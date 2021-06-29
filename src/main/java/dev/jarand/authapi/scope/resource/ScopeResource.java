package dev.jarand.authapi.scope.resource;

public class ScopeResource {

    private final String id;
    private final String description;
    private final String timeOfCreation;

    public ScopeResource(String id, String description, String timeOfCreation) {
        this.id = id;
        this.description = description;
        this.timeOfCreation = timeOfCreation;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getTimeOfCreation() {
        return timeOfCreation;
    }
}
