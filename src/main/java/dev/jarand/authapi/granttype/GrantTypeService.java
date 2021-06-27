package dev.jarand.authapi.granttype;

import dev.jarand.authapi.granttype.domain.GrantType;
import dev.jarand.authapi.granttype.repository.GrantTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GrantTypeService {

    private final GrantTypeRepository repository;

    public GrantTypeService(GrantTypeRepository repository) {
        this.repository = repository;
    }

    public void create(GrantType grantType) {
        repository.create(grantType);
    }

    public List<GrantType> get() {
        return repository.get();
    }

    public Optional<GrantType> get(String grantType) {
        return repository.get(grantType);
    }
}
