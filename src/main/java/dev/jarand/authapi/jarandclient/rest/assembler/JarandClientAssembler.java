package dev.jarand.authapi.jarandclient.rest.assembler;

import dev.jarand.authapi.jarandclient.domain.JarandClient;
import dev.jarand.authapi.jarandclient.rest.resource.CreateJarandClientResource;
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

    public JarandClient assembleNew(CreateJarandClientResource resource) {
        return new JarandClient(
                uuidSupplier.get(),
                resource.getClientId(),
                passwordEncoder.encode(resource.getClientSecret()),
                UUID.fromString(resource.getOwnerId()),
                instantSupplier.get());
    }
}
