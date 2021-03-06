package dev.jarand.authapi.scopeconnection.assembler;

import dev.jarand.authapi.scopeconnection.domain.ScopeConnection;
import dev.jarand.authapi.scopeconnection.resource.ScopeConnectionResource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ScopeConnectionResourceAssembler {

    public ScopeConnectionResource assemble(ScopeConnection scopeConnection) {
        return new ScopeConnectionResource(scopeConnection.getScopeId(), scopeConnection.getClientId(), scopeConnection.getTimeOfCreation().toString());
    }

    public List<ScopeConnectionResource> assemble(List<ScopeConnection> scopeConnections) {
        return scopeConnections.stream().map(this::assemble).toList();
    }
}
