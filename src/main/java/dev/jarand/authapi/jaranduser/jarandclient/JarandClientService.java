package dev.jarand.authapi.jaranduser.jarandclient;

import dev.jarand.authapi.jaranduser.jarandclient.domain.JarandClient;
import dev.jarand.authapi.jaranduser.jarandclient.repository.JarandClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class JarandClientService {

    private final JarandClientRepository repository;

    public JarandClientService(JarandClientRepository repository) {
        this.repository = repository;
    }

    public Optional<JarandClient> getClientByClientId(String clientId) {
        return repository.getClientByClientId(clientId);
    }

    public List<JarandClient> getClients(UUID ownerId) {
        return repository.getClients(ownerId);
    }

    public void createClient(JarandClient jarandClient) {
        repository.createClient(jarandClient);
    }
}
