package dev.jarand.authapi.jaranduser.rest.assembler;

import dev.jarand.authapi.jaranduser.domain.JarandUser;
import dev.jarand.authapi.jaranduser.jarandclient.JarandClientService;
import dev.jarand.authapi.jaranduser.jarandclient.LoginClientService;
import dev.jarand.authapi.jaranduser.jarandclient.rest.assembler.JarandClientResourceAssembler;
import dev.jarand.authapi.jaranduser.jarandclient.rest.assembler.LoginClientResourceAssembler;
import dev.jarand.authapi.jaranduser.rest.resource.JarandUserResource;
import org.springframework.stereotype.Component;

@Component
public class JarandUserResourceAssembler {

    private final JarandClientService jarandClientService;
    private final JarandClientResourceAssembler jarandClientResourceAssembler;
    private final LoginClientService loginClientService;
    private final LoginClientResourceAssembler loginClientResourceAssembler;

    public JarandUserResourceAssembler(JarandClientService jarandClientService,
                                       JarandClientResourceAssembler jarandClientResourceAssembler,
                                       LoginClientService loginClientService,
                                       LoginClientResourceAssembler loginClientResourceAssembler) {
        this.jarandClientService = jarandClientService;
        this.jarandClientResourceAssembler = jarandClientResourceAssembler;
        this.loginClientService = loginClientService;
        this.loginClientResourceAssembler = loginClientResourceAssembler;
    }

    public JarandUserResource assemble(JarandUser jarandUser) {
        return new JarandUserResource(
                jarandUser.getId().toString(),
                jarandUser.getEmail(),
                jarandUser.getUsername(),
                jarandUser.getDisplayName(),
                jarandUser.getTimeOfCreation().toString(),
                loginClientResourceAssembler.assemble(loginClientService.getClient(jarandUser.getId())),
                jarandClientResourceAssembler.assemble(jarandClientService.getClients(jarandUser.getId())));
    }
}
