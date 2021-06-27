package dev.jarand.authapi.granttype.assembler;

import dev.jarand.authapi.granttype.domain.GrantType;
import dev.jarand.authapi.granttype.resource.GrantTypeResource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GrantTypeResourceAssembler {

    public GrantTypeResource assemble(GrantType grantType) {
        return new GrantTypeResource(grantType.getGrantType(), grantType.getTimeOfCreation().toString());
    }

    public List<GrantTypeResource> assemble(List<GrantType> grantTypes) {
        return grantTypes.stream().map(this::assemble).toList();
    }
}
