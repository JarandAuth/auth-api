package dev.jarand.authapi.granttype.assembler;

import dev.jarand.authapi.granttype.domain.GrantType;
import dev.jarand.authapi.granttype.resource.CreateGrantTypeResource;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.function.Supplier;

@Component
public class GrantTypeAssembler {

    private final Supplier<Instant> instantSupplier;

    public GrantTypeAssembler(Supplier<Instant> instantSupplier) {
        this.instantSupplier = instantSupplier;
    }

    public GrantType assembleNew(CreateGrantTypeResource resource) {
        return new GrantType(resource.getGrantType(), instantSupplier.get());
    }
}
