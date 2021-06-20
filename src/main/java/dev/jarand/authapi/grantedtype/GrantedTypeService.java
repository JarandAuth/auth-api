package dev.jarand.authapi.grantedtype;

import dev.jarand.authapi.grantedtype.domain.GrantedType;
import dev.jarand.authapi.grantedtype.repository.GrantedTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GrantedTypeService {

    private final GrantedTypeRepository repository;

    public GrantedTypeService(GrantedTypeRepository repository) {
        this.repository = repository;
    }

    public void create(GrantedType grantedType) {
        repository.create(grantedType);
    }

    public List<GrantedType> get(String jarandClientId) {
        return repository.get(jarandClientId);
    }

    public Optional<GrantedType> get(String grantType, String jarandClientId) {
        return repository.get(grantType, jarandClientId);
    }
}
