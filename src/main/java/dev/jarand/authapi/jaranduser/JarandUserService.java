package dev.jarand.authapi.jaranduser;

import dev.jarand.authapi.jaranduser.domain.JarandUser;
import dev.jarand.authapi.jaranduser.jarandclient.JarandClientService;
import dev.jarand.authapi.jaranduser.jarandclient.rest.assembler.LoginClientAssembler;
import dev.jarand.authapi.jaranduser.jarandclient.rest.assembler.SecretClientAssembler;
import dev.jarand.authapi.jaranduser.repository.JarandUserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class JarandUserService {

    private final JarandUserRepository repository;
    private final JarandClientService jarandClientService;
    private final SecretClientAssembler secretClientAssembler;
    private final LoginClientAssembler loginClientAssembler;

    public JarandUserService(JarandUserRepository repository,
                             JarandClientService jarandClientService,
                             SecretClientAssembler secretClientAssembler,
                             LoginClientAssembler loginClientAssembler) {
        this.repository = repository;
        this.jarandClientService = jarandClientService;
        this.secretClientAssembler = secretClientAssembler;
        this.loginClientAssembler = loginClientAssembler;
    }

    public Optional<JarandUser> getUser(UUID id) {
        return repository.getUser(id);
    }

    public void createUser(JarandUser user, String password) {
        final var secretClient = secretClientAssembler.assembleNew(user, password);
        final var loginClient = loginClientAssembler.assembleNew(user, password);
        repository.createUser(user);
        jarandClientService.createSecretClient(secretClient);
        jarandClientService.createLoginClient(loginClient);
    }
}
