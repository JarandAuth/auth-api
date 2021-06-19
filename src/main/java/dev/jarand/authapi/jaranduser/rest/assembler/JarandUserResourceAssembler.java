package dev.jarand.authapi.jaranduser.rest.assembler;

import dev.jarand.authapi.jaranduser.domain.JarandUser;
import dev.jarand.authapi.jaranduser.rest.resource.JarandUserResource;
import org.springframework.stereotype.Component;

@Component
public class JarandUserResourceAssembler {

    public JarandUserResource assemble(JarandUser jarandUser) {
        return new JarandUserResource(
                jarandUser.getId().toString(),
                jarandUser.getEmail(),
                jarandUser.getUsername(),
                jarandUser.getDisplayName(),
                jarandUser.getPassword(),
                jarandUser.getTimeOfCreation().toString());
    }
}
