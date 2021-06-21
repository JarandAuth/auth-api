package dev.jarand.authapi.jaranduser;

import dev.jarand.authapi.jaranduser.domain.JarandUser;
import dev.jarand.authapi.jaranduser.jarandclient.JarandClientService;
import dev.jarand.authapi.jaranduser.jarandclient.LoginClientService;
import dev.jarand.authapi.jaranduser.jarandclient.rest.assembler.JarandClientAssembler;
import dev.jarand.authapi.jaranduser.jarandclient.rest.assembler.LoginClientAssembler;
import dev.jarand.authapi.jaranduser.repository.JarandUserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class JarandUserService {

    private final JarandUserRepository repository;
    private final LoginClientService loginClientService;
    private final LoginClientAssembler loginClientAssembler;
    private final JarandClientService jarandClientService;
    private final JarandClientAssembler jarandClientAssembler;

    public JarandUserService(JarandUserRepository repository,
                             LoginClientService loginClientService,
                             LoginClientAssembler loginClientAssembler,
                             JarandClientService jarandClientService,
                             JarandClientAssembler jarandClientAssembler) {
        this.repository = repository;
        this.loginClientService = loginClientService;
        this.loginClientAssembler = loginClientAssembler;
        this.jarandClientService = jarandClientService;
        this.jarandClientAssembler = jarandClientAssembler;
    }

    public Optional<JarandUser> getUser(UUID id) {
        return repository.getUser(id);
    }

    public void createUser(JarandUser user, String password) {
        final var loginClient = loginClientAssembler.assembleNew(user, password);
        final var jarandClient = jarandClientAssembler.assembleNew(user, password);
        repository.createUser(user);
        loginClientService.createClient(loginClient);
        jarandClientService.createClient(jarandClient);
    }
}
