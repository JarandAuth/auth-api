package dev.jarand.authapi.grantedtype.assembler;

import dev.jarand.authapi.grantedtype.domain.GrantedType;
import dev.jarand.authapi.grantedtype.resource.CreateGrantedTypeResource;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.function.Supplier;

@Component
public class GrantedTypeAssembler {

    private final Supplier<Instant> instantSupplier;

    public GrantedTypeAssembler(Supplier<Instant> instantSupplier) {
        this.instantSupplier = instantSupplier;
    }

    public GrantedType assembleNew(String grantType, String clientId) {
        return new GrantedType(grantType, clientId, instantSupplier.get());
    }

    public GrantedType assembleNew(CreateGrantedTypeResource resource) {
        return new GrantedType(resource.getGrantType(), resource.getClientId(), instantSupplier.get());
    }
}
