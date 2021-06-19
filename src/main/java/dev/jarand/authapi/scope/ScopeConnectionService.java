package dev.jarand.authapi.scope;

import dev.jarand.authapi.scope.domain.ScopeConnection;
import dev.jarand.authapi.scope.repository.ScopeConnectionRepository;
import org.springframework.stereotype.Service;

@Service
public class ScopeConnectionService {

    private final ScopeConnectionRepository repository;

    public ScopeConnectionService(ScopeConnectionRepository repository) {
        this.repository = repository;
    }

    public void create(ScopeConnection scopeConnection) {
        repository.create(scopeConnection);
    }
}
