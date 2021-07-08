package dev.jarand.authapi.jarandclient.rest.assembler;

import dev.jarand.authapi.jarandclient.domain.SecretClient;
import dev.jarand.authapi.jarandclient.rest.resource.CreateJarandClientResource;
import dev.jarand.authapi.jaranduser.domain.JarandUser;
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
                resource.getDisplayName(),
                ownerId,
                instantSupplier.get(),
                passwordEncoder.encode(resource.getClientSecret()));
    }

    public SecretClient assembleNew(JarandUser user, String password) {
        return new SecretClient(
                uuidSupplier.get().toString(),
                "SECRET",
                user.getDisplayName(),
                user.getId(),
                user.getTimeOfCreation(),
                passwordEncoder.encode(password));
    }
}
