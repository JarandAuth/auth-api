package dev.jarand.authapi.jaranduser.rest.assembler;

import dev.jarand.authapi.jaranduser.domain.JarandUser;
import dev.jarand.authapi.jaranduser.jarandclient.domain.JarandClient;
import dev.jarand.authapi.jaranduser.jarandclient.rest.assembler.JarandClientResourceAssembler;
import dev.jarand.authapi.jaranduser.rest.resource.JarandUserResource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JarandUserResourceAssembler {

    private final JarandClientResourceAssembler jarandClientResourceAssembler;

    public JarandUserResourceAssembler(JarandClientResourceAssembler jarandClientResourceAssembler) {
        this.jarandClientResourceAssembler = jarandClientResourceAssembler;
    }

    public JarandUserResource assemble(JarandUser jarandUser, List<JarandClient> jarandClients) {
        return new JarandUserResource(
                jarandUser.getId().toString(),
                jarandUser.getEmail(),
                jarandUser.getUsername(),
                jarandUser.getDisplayName(),
                jarandUser.getPassword(),
                jarandUser.getTimeOfCreation().toString(),
                jarandClientResourceAssembler.assemble(jarandClients));
    }
}
