package dev.jarand.authapi.jaranduser.jarandclient.rest.assembler;

import dev.jarand.authapi.jaranduser.domain.JarandUser;
import dev.jarand.authapi.jaranduser.jarandclient.domain.LoginClient;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.function.Supplier;

@Component
public class LoginClientAssembler {

    private final Supplier<UUID> uuidSupplier;
    private final PasswordEncoder passwordEncoder;

    public LoginClientAssembler(Supplier<UUID> uuidSupplier, PasswordEncoder passwordEncoder) {
        this.uuidSupplier = uuidSupplier;
        this.passwordEncoder = passwordEncoder;
    }

    public LoginClient assembleNew(JarandUser jarandUser, String password) {
        return new LoginClient(
                uuidSupplier.get().toString(),
                jarandUser.getUsername(),
                passwordEncoder.encode(password),
                jarandUser.getId(),
                jarandUser.getTimeOfCreation());
    }
}
