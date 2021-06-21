package dev.jarand.authapi.oauth.domain;

import java.util.Optional;

public class PasswordParameters {

    private final String username;
    private final String password;
    private final String scope;

    public PasswordParameters(String username, String password, String scope) {
        this.username = username;
        this.password = password;
        this.scope = scope;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Optional<String> getScope() {
        return Optional.ofNullable(scope);
    }
}
