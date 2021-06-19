package dev.jarand.authapi.jaranduser.rest.resource;

import javax.validation.constraints.NotNull;

public class CreateJarandUserResource {

    private final String email;
    private final String username;
    private final String displayName;
    private final String password;

    public CreateJarandUserResource(String email, String username, String displayName, String password) {
        this.email = email;
        this.username = username;
        this.displayName = displayName;
        this.password = password;
    }

    @NotNull
    public String getEmail() {
        return email;
    }

    @NotNull
    public String getUsername() {
        return username;
    }

    @NotNull
    public String getDisplayName() {
        return displayName;
    }

    @NotNull
    public String getPassword() {
        return password;
    }
}
