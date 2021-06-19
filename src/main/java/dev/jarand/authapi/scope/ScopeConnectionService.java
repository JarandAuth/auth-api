package dev.jarand.authapi.scope;

import dev.jarand.authapi.scope.domain.ScopeConnection;
import dev.jarand.authapi.scope.repository.ScopeConnectionRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ScopeConnectionService {

    private final ScopeConnectionRepository repository;

    public ScopeConnectionService(ScopeConnectionRepository repository) {
        this.repository = repository;
    }

    public void create(ScopeConnection scopeConnection) {
        repository.create(scopeConnection);
    }

    public Optional<ScopeConnection> get(String scopeId, UUID jarandClientId) {
        return repository.get(scopeId, jarandClientId);
    }
}
