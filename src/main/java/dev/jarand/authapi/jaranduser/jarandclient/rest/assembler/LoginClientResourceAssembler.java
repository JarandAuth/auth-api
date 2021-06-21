package dev.jarand.authapi.jaranduser.jarandclient.rest.assembler;

import dev.jarand.authapi.grantedtype.GrantedTypeService;
import dev.jarand.authapi.grantedtype.assembler.GrantedTypeResourceAssembler;
import dev.jarand.authapi.jaranduser.jarandclient.domain.LoginClient;
import dev.jarand.authapi.jaranduser.jarandclient.rest.resource.LoginClientResource;
import org.springframework.stereotype.Component;

@Component
public class LoginClientResourceAssembler {

    private final GrantedTypeService grantedTypeService;
    private final GrantedTypeResourceAssembler grantedTypeResourceAssembler;

    public LoginClientResourceAssembler(GrantedTypeService grantedTypeService, GrantedTypeResourceAssembler grantedTypeResourceAssembler) {
        this.grantedTypeService = grantedTypeService;
        this.grantedTypeResourceAssembler = grantedTypeResourceAssembler;
    }

    public LoginClientResource assemble(LoginClient loginClient) {
        return new LoginClientResource(
                loginClient.getClientId(),
                loginClient.getUsername(),
                loginClient.getPassword(),
                loginClient.getOwnerId().toString(),
                loginClient.getTimeOfCreation().toString(),
                grantedTypeResourceAssembler.assemble(grantedTypeService.get(loginClient.getClientId())));
    }
}
