package dev.jarand.authapi.scope;

import dev.jarand.authapi.scope.domain.Scope;
import dev.jarand.authapi.scope.repository.ScopeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ScopeService {

    private final ScopeRepository repository;

    public ScopeService(ScopeRepository repository) {
        this.repository = repository;
    }

    public void create(Scope scope) {
        repository.create(scope);
    }

    public List<Scope> getScopes() {
        return repository.getScopes();
    }

    public Optional<Scope> getScope(String id) {
        return repository.getScope(id);
    }
}
