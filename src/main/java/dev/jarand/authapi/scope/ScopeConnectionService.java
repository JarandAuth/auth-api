package dev.jarand.authapi.scope;

import dev.jarand.authapi.scope.domain.ScopeConnection;
import dev.jarand.authapi.scope.repository.ScopeConnectionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ScopeConnectionService {

    private final ScopeConnectionRepository repository;

    public ScopeConnectionService(ScopeConnectionRepository repository) {
        this.repository = repository;
    }

    public void create(ScopeConnection scopeConnection) {
        repository.create(scopeConnection);
    }

    public List<ScopeConnection> get(String scopeId) {
        return repository.get(scopeId);
    }

    public Optional<ScopeConnection> get(String scopeId, String clientId) {
        return repository.get(scopeId, clientId);
    }
}
