package dev.jarand.authapi.scopeconnection;

import dev.jarand.authapi.scopeconnection.domain.ScopeConnection;
import dev.jarand.authapi.scopeconnection.repository.ScopeConnectionRepository;
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

    public Optional<ScopeConnection> get(String scopeId, String clientId) {
        return repository.get(scopeId, clientId);
    }

    public List<ScopeConnection> get() {
        return repository.get();
    }

    public List<ScopeConnection> getByScopeId(String scopeId) {
        return repository.getByScopeId(scopeId);
    }

    public List<ScopeConnection> getByClientId(String clientId) {
        return repository.getByClientId(clientId);
    }
}
