package dev.jarand.authapi.jaranduser.jarandclient;

import dev.jarand.authapi.grantedtype.GrantedTypeService;
import dev.jarand.authapi.grantedtype.domain.GrantedType;
import dev.jarand.authapi.jaranduser.jarandclient.domain.JarandClient;
import dev.jarand.authapi.jaranduser.jarandclient.domain.LoginClient;
import dev.jarand.authapi.jaranduser.jarandclient.domain.SecretClient;
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

    public Optional<LoginClient> getLoginClient(String username) {
        return repository.getLoginClient(username);
    }

    public List<JarandClient> getClients(UUID ownerId) {
        return repository.getClients(ownerId);
    }

    public void createSecretClient(SecretClient client) {
        repository.createSecretClient(client);
        grantedTypeService.create(new GrantedType("client_credentials", client.getClientId()));
        grantedTypeService.create(new GrantedType("refresh_token", client.getClientId()));
    }

    public void createLoginClient(LoginClient client) {
        repository.createLoginClient(client);
        grantedTypeService.create(new GrantedType("password", client.getClientId()));
        grantedTypeService.create(new GrantedType("refresh_token", client.getClientId()));
    }
}
