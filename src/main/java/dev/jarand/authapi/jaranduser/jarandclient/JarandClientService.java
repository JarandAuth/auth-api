package dev.jarand.authapi.jaranduser.jarandclient;

import dev.jarand.authapi.grantedtype.GrantedTypeService;
import dev.jarand.authapi.grantedtype.domain.GrantedType;
import dev.jarand.authapi.jaranduser.jarandclient.domain.JarandClient;
import dev.jarand.authapi.jaranduser.jarandclient.repository.JarandClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class JarandClientService {

    private final JarandClientRepository repository;
    private final GrantedTypeService grantedTypeService;

    public JarandClientService(JarandClientRepository repository, GrantedTypeService grantedTypeService) {
        this.repository = repository;
        this.grantedTypeService = grantedTypeService;
    }

    public Optional<JarandClient> getClient(String clientId) {
        return repository.getClient(clientId);
    }

    public List<JarandClient> getClients(UUID ownerId) {
        return repository.getClients(ownerId);
    }

    public void createClient(JarandClient jarandClient) {
        repository.createClient(jarandClient);
        grantedTypeService.create(new GrantedType("client_credentials", jarandClient.getClientId()));
        grantedTypeService.create(new GrantedType("refresh_token", jarandClient.getClientId()));
    }
}
