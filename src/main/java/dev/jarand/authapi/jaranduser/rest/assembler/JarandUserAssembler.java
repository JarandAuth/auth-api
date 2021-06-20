package dev.jarand.authapi.jaranduser.rest.assembler;

import dev.jarand.authapi.jaranduser.domain.JarandUser;
import dev.jarand.authapi.jaranduser.rest.resource.CreateJarandUserResource;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;
import java.util.function.Supplier;

@Component
public class JarandUserAssembler {

    private final Supplier<UUID> uuidSupplier;
    private final Supplier<Instant> instantSupplier;

    public JarandUserAssembler(Supplier<UUID> uuidSupplier, Supplier<Instant> instantSupplier) {
        this.uuidSupplier = uuidSupplier;
        this.instantSupplier = instantSupplier;
    }

    public JarandUser assembleNew(CreateJarandUserResource resource) {
        return new JarandUser(
                uuidSupplier.get(),
                resource.getEmail(),
                resource.getUsername(),
                resource.getDisplayName(),
                instantSupplier.get());
    }
}
