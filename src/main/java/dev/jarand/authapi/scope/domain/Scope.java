package dev.jarand.authapi.scope.domain;

import java.time.Instant;

public class Scope {

    private final String id;
    private final String description;
    private final Instant timeOfCreation;

    public Scope(String id, String description, Instant timeOfCreation) {
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

    public Instant getTimeOfCreation() {
        return timeOfCreation;
    }
}
