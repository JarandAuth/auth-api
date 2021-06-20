package dev.jarand.authapi.jaranduser.jarandclient.rest.assembler;

import dev.jarand.authapi.jaranduser.domain.JarandUser;
import dev.jarand.authapi.jaranduser.jarandclient.domain.JarandClient;
import dev.jarand.authapi.jaranduser.jarandclient.rest.resource.CreateJarandClientResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;
import java.util.function.Supplier;

@Component
public class JarandClientAssembler {

    private final Supplier<UUID> uuidSupplier;
    private final Supplier<Instant> instantSupplier;
    private final PasswordEncoder passwordEncoder;

    public JarandClientAssembler(Supplier<UUID> uuidSupplier, Supplier<Instant> instantSupplier, PasswordEncoder passwordEncoder) {
        this.uuidSupplier = uuidSupplier;
        this.instantSupplier = instantSupplier;
        this.passwordEncoder = passwordEncoder;
    }

    public JarandClient assembleNew(CreateJarandClientResource resource, UUID ownerId) {
        return new JarandClient(
                uuidSupplier.get(),
                resource.getClientId(),
                passwordEncoder.encode(resource.getClientSecret()),
                ownerId,
                instantSupplier.get());
    }

    public JarandClient assembleNew(JarandUser jarandUser, String password) {
        return new JarandClient(
                uuidSupplier.get(),
                jarandUser.getEmail(),
                passwordEncoder.encode(password),
                jarandUser.getId(),
                jarandUser.getTimeOfCreation());
    }
}
