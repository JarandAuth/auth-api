package dev.jarand.authapi.jaranduser.rest.assembler;

import dev.jarand.authapi.jarandclient.JarandClientService;
import dev.jarand.authapi.jarandclient.rest.assembler.JarandClientResourceAssembler;
import dev.jarand.authapi.jaranduser.domain.JarandUser;
import dev.jarand.authapi.jaranduser.rest.resource.JarandUserResource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JarandUserResourceAssembler {

    private final JarandClientService jarandClientService;
    private final JarandClientResourceAssembler jarandClientResourceAssembler;

    public JarandUserResourceAssembler(JarandClientService jarandClientService,
                                       JarandClientResourceAssembler jarandClientResourceAssembler) {
        this.jarandClientService = jarandClientService;
        this.jarandClientResourceAssembler = jarandClientResourceAssembler;
    }

    public JarandUserResource assemble(JarandUser user) {
        return new JarandUserResource(
                user.getId().toString(),
                user.getEmail(),
                user.getUsername(),
                user.getDisplayName(),
                user.getTimeOfCreation().toString(),
                jarandClientResourceAssembler.assemble(jarandClientService.getClients(user.getId())));
    }

    public List<JarandUserResource> assemble(List<JarandUser> users) {
        return users.stream().map(this::assemble).toList();
    }
}
