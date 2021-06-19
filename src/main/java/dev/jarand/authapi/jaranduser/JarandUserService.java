package dev.jarand.authapi.jaranduser;

import dev.jarand.authapi.jaranduser.domain.JarandUser;
import dev.jarand.authapi.jaranduser.repository.JarandUserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class JarandUserService {

    private final JarandUserRepository repository;

    public JarandUserService(JarandUserRepository repository) {
        this.repository = repository;
    }

    public Optional<JarandUser> getUser(UUID id) {
        return repository.getUser(id);
    }

    public Optional<JarandUser> getUserByUsername(String username) {
        return repository.getUserByUsername(username);
    }

    public void createUser(JarandUser jarandUser) {
        repository.createUser(jarandUser);
    }
}
