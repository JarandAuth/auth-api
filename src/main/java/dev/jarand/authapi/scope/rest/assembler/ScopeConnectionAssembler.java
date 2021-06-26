package dev.jarand.authapi.scope.rest.assembler;

import dev.jarand.authapi.scope.domain.ScopeConnection;
import dev.jarand.authapi.scope.rest.resource.CreateScopeConnectionResource;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.function.Supplier;

@Component
public class ScopeConnectionAssembler {

    private final Supplier<Instant> instantSupplier;

    public ScopeConnectionAssembler(Supplier<Instant> instantSupplier) {
        this.instantSupplier = instantSupplier;
    }

    public ScopeConnection assembleNew(String scopeId, CreateScopeConnectionResource resource) {
        return new ScopeConnection(scopeId, resource.getClientId(), instantSupplier.get());
    }
}
