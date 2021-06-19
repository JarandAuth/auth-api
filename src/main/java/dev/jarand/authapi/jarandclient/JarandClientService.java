package dev.jarand.authapi.jarandclient;

import dev.jarand.authapi.jarandclient.domain.JarandClient;
import dev.jarand.authapi.jarandclient.repository.JarandClientRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JarandClientService {

    private final JarandClientRepository repository;

    public JarandClientService(JarandClientRepository repository) {
        this.repository = repository;
    }

    public Optional<JarandClient> getClientByClientId(String clientId) {
        return repository.getClientByClientId(clientId);
    }

    public void createClient(JarandClient jarandClient) {
        repository.createClient(jarandClient);
    }
}
