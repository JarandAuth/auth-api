package dev.jarand.authapi.jaranduser.jarandclient;

import dev.jarand.authapi.grantedtype.GrantedTypeService;
import dev.jarand.authapi.grantedtype.domain.GrantedType;
import dev.jarand.authapi.jaranduser.jarandclient.domain.LoginClient;
import dev.jarand.authapi.jaranduser.jarandclient.repository.LoginClientRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class LoginClientService {

    private final LoginClientRepository repository;
    private final GrantedTypeService grantedTypeService;

    public LoginClientService(LoginClientRepository repository, GrantedTypeService grantedTypeService) {
        this.repository = repository;
        this.grantedTypeService = grantedTypeService;
    }

    public void createClient(LoginClient loginClient) {
        repository.createClient(loginClient);
        grantedTypeService.create(new GrantedType("password", loginClient.getClientId()));
    }

    public Optional<LoginClient> getClient(String username) {
        return repository.getClient(username);
    }

    public LoginClient getClient(UUID ownerId) {
        return repository.getClient(ownerId);
    }
}
