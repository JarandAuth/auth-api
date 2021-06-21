package dev.jarand.authapi.scope.rest.assembler;

import dev.jarand.authapi.scope.domain.ScopeConnection;
import dev.jarand.authapi.scope.rest.resource.ScopeConnectionResource;
import org.springframework.stereotype.Component;

@Component
public class ScopeConnectionResourceAssembler {

    public ScopeConnectionResource assemble(ScopeConnection scopeConnection) {
        return new ScopeConnectionResource(scopeConnection.getScopeId(), scopeConnection.getClientId());
    }
}
