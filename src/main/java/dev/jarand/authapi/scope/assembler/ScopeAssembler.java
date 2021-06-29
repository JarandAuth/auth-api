package dev.jarand.authapi.scope.assembler;

import dev.jarand.authapi.scope.domain.Scope;
import dev.jarand.authapi.scope.resource.CreateScopeResource;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.function.Supplier;

@Component
public class ScopeAssembler {

    private final Supplier<Instant> instantSupplier;

    public ScopeAssembler(Supplier<Instant> instantSupplier) {
        this.instantSupplier = instantSupplier;
    }

    public Scope assembleNew(CreateScopeResource resource) {
        return new Scope(
                resource.getId(),
                resource.getDescription(),
                instantSupplier.get());
    }
}
