package dev.jarand.authapi.scope.assembler;

import dev.jarand.authapi.scope.domain.Scope;
import dev.jarand.authapi.scope.resource.ScopeResource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ScopeResourceAssembler {

    public List<ScopeResource> assemble(List<Scope> scopes) {
        return scopes.stream().map(this::assemble).toList();
    }

    public ScopeResource assemble(Scope scope) {
        return new ScopeResource(scope.getId(), scope.getDescription(), scope.getTimeOfCreation().toString());
    }
}
