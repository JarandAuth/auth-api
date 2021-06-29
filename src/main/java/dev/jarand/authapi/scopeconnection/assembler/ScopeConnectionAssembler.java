package dev.jarand.authapi.scopeconnection.assembler;

import dev.jarand.authapi.scopeconnection.domain.ScopeConnection;
import dev.jarand.authapi.scopeconnection.resource.CreateScopeConnectionResource;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.function.Supplier;

@Component
public class ScopeConnectionAssembler {

    private final Supplier<Instant> instantSupplier;

    public ScopeConnectionAssembler(Supplier<Instant> instantSupplier) {
        this.instantSupplier = instantSupplier;
    }

    public ScopeConnection assembleNew(CreateScopeConnectionResource resource) {
        return new ScopeConnection(resource.getScopeId(), resource.getClientId(), instantSupplier.get());
    }
}
