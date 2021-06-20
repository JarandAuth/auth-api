package dev.jarand.authapi.jaranduser.rest.assembler;

import dev.jarand.authapi.jaranduser.domain.JarandUser;
import dev.jarand.authapi.jaranduser.jarandclient.JarandClientService;
import dev.jarand.authapi.jaranduser.jarandclient.rest.assembler.JarandClientResourceAssembler;
import dev.jarand.authapi.jaranduser.rest.resource.JarandUserResource;
import org.springframework.stereotype.Component;

@Component
public class JarandUserResourceAssembler {

    private final JarandClientService jarandClientService;
    private final JarandClientResourceAssembler jarandClientResourceAssembler;

    public JarandUserResourceAssembler(JarandClientService jarandClientService, JarandClientResourceAssembler jarandClientResourceAssembler) {
        this.jarandClientService = jarandClientService;
        this.jarandClientResourceAssembler = jarandClientResourceAssembler;
    }

    public JarandUserResource assemble(JarandUser jarandUser) {
        return new JarandUserResource(
                jarandUser.getId().toString(),
                jarandUser.getEmail(),
                jarandUser.getUsername(),
                jarandUser.getDisplayName(),
                jarandUser.getTimeOfCreation().toString(),
                jarandClientResourceAssembler.assemble(jarandClientService.getClients(jarandUser.getId())));
    }
}
