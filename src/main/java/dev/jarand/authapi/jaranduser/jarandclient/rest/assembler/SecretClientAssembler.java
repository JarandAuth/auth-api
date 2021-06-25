package dev.jarand.authapi.jaranduser.jarandclient.rest.assembler;

import dev.jarand.authapi.jaranduser.domain.JarandUser;
import dev.jarand.authapi.jaranduser.jarandclient.domain.SecretClient;
import dev.jarand.authapi.jaranduser.jarandclient.rest.resource.CreateJarandClientResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;
import java.util.function.Supplier;

@Component
public class SecretClientAssembler {

    private final Supplier<UUID> uuidSupplier;
    private final Supplier<Instant> instantSupplier;
    private final PasswordEncoder passwordEncoder;

    public SecretClientAssembler(Supplier<UUID> uuidSupplier, Supplier<Instant> instantSupplier, PasswordEncoder passwordEncoder) {
        this.uuidSupplier = uuidSupplier;
        this.instantSupplier = instantSupplier;
        this.passwordEncoder = passwordEncoder;
    }

    public SecretClient assembleNew(CreateJarandClientResource resource, UUID ownerId) {
        return new SecretClient(
                resource.getClientId(),
                "SECRET",
                ownerId,
                instantSupplier.get(),
                passwordEncoder.encode(resource.getClientSecret()));
    }

    public SecretClient assembleNew(JarandUser jarandUser, String password) {
        return new SecretClient(
                uuidSupplier.get().toString(),
                "SECRET",
                jarandUser.getId(),
                jarandUser.getTimeOfCreation(),
                passwordEncoder.encode(password));
    }
}
