package dev.jarand.authapi.jarandclient.rest.resource;

public class ErrorResource {

    private final String error;

    public ErrorResource(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }
}
