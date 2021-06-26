package dev.jarand.authapi.jarandclient;

import dev.jarand.authapi.grantedtype.GrantedTypeService;
import dev.jarand.authapi.grantedtype.assembler.GrantedTypeAssembler;
import dev.jarand.authapi.jarandclient.domain.JarandClient;
import dev.jarand.authapi.jarandclient.domain.LoginClient;
import dev.jarand.authapi.jarandclient.domain.SecretClient;
import dev.jarand.authapi.jarandclient.repository.JarandClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class JarandClientService {

    private final JarandClientRepository repository;
    private final GrantedTypeService grantedTypeService;
    private final GrantedTypeAssembler grantedTypeAssembler;

    public JarandClientService(JarandClientRepository repository, GrantedTypeService grantedTypeService, GrantedTypeAssembler grantedTypeAssembler) {
        this.repository = repository;
        this.grantedTypeService = grantedTypeService;
        this.grantedTypeAssembler = grantedTypeAssembler;
    }

    public List<JarandClient> getClients() {
        return repository.getClients();
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
        grantedTypeService.create(grantedTypeAssembler.assembleNew("client_credentials", client.getClientId()));
        grantedTypeService.create(grantedTypeAssembler.assembleNew("refresh_token", client.getClientId()));
    }

    public void createLoginClient(LoginClient client) {
        repository.createLoginClient(client);
        grantedTypeService.create(grantedTypeAssembler.assembleNew("password", client.getClientId()));
        grantedTypeService.create(grantedTypeAssembler.assembleNew("refresh_token", client.getClientId()));
    }
}
