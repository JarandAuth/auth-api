package dev.jarand.authapi.jaranduser;

import dev.jarand.authapi.jaranduser.domain.JarandUser;
import dev.jarand.authapi.jaranduser.jarandclient.JarandClientService;
import dev.jarand.authapi.jaranduser.jarandclient.rest.assembler.JarandClientAssembler;
import dev.jarand.authapi.jaranduser.repository.JarandUserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class JarandUserService {

    private final JarandUserRepository repository;
    private final JarandClientService jarandClientService;
    private final JarandClientAssembler jarandClientAssembler;

    public JarandUserService(JarandUserRepository repository, JarandClientService jarandClientService, JarandClientAssembler jarandClientAssembler) {
        this.repository = repository;
        this.jarandClientService = jarandClientService;
        this.jarandClientAssembler = jarandClientAssembler;
    }

    public Optional<JarandUser> getUser(UUID id) {
        return repository.getUser(id);
    }

    public Optional<JarandUser> getUserByUsername(String username) {
        return repository.getUserByUsername(username);
    }

    public void createUser(JarandUser user) {
        final var client = jarandClientAssembler.assembleNew(user);
        repository.createUser(user);
        jarandClientService.createClient(client);
    }
}
