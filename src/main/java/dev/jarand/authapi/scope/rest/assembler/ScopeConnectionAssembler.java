package dev.jarand.authapi.scope.rest.assembler;

import dev.jarand.authapi.scope.domain.ScopeConnection;
import dev.jarand.authapi.scope.rest.resource.CreateScopeConnectionResource;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ScopeConnectionAssembler {

    public ScopeConnection assembleNew(String scopeId, CreateScopeConnectionResource resource) {
        return new ScopeConnection(scopeId, UUID.fromString(resource.getJarandClientId()));
    }
}
