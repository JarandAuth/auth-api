package dev.jarand.authapi.grantedtype.assembler;

import dev.jarand.authapi.grantedtype.domain.GrantedType;
import dev.jarand.authapi.grantedtype.resource.GrantedTypeResource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GrantedTypeResourceAssembler {

    public List<GrantedTypeResource> assemble(List<GrantedType> grantedTypes) {
        return grantedTypes.stream().map(this::assemble).toList();
    }

    private GrantedTypeResource assemble(GrantedType grantedType) {
        return new GrantedTypeResource(grantedType.getGrantType(), grantedType.getClientId());
    }
}
