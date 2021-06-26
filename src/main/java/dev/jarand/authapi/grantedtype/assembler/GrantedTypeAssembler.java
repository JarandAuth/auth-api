package dev.jarand.authapi.grantedtype.assembler;

import dev.jarand.authapi.grantedtype.domain.GrantedType;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.function.Supplier;

@Component
public class GrantedTypeAssembler {

    private final Supplier<Instant> instantSupplier;

    public GrantedTypeAssembler(Supplier<Instant> instantSupplier) {
        this.instantSupplier = instantSupplier;
    }

    public GrantedType assembleNew(String grantType, String scopeId) {
        return new GrantedType(grantType, scopeId, instantSupplier.get());
    }
}
