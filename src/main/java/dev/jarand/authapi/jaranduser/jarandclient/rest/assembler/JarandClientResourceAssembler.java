package dev.jarand.authapi.jaranduser.jarandclient.rest.assembler;

import dev.jarand.authapi.grantedtype.GrantedTypeService;
import dev.jarand.authapi.grantedtype.assembler.GrantedTypeResourceAssembler;
import dev.jarand.authapi.jaranduser.jarandclient.domain.JarandClient;
import dev.jarand.authapi.jaranduser.jarandclient.rest.resource.JarandClientResource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JarandClientResourceAssembler {

    private final GrantedTypeService grantedTypeService;
    private final GrantedTypeResourceAssembler grantedTypeResourceAssembler;

    public JarandClientResourceAssembler(GrantedTypeService grantedTypeService, GrantedTypeResourceAssembler grantedTypeResourceAssembler) {
        this.grantedTypeService = grantedTypeService;
        this.grantedTypeResourceAssembler = grantedTypeResourceAssembler;
    }

    public JarandClientResource assemble(JarandClient jarandClient) {
        return new JarandClientResource(
                jarandClient.getClientId(),
                jarandClient.getClientSecret(),
                jarandClient.getOwnerId().toString(),
                jarandClient.getTimeOfCreation().toString(),
                grantedTypeResourceAssembler.assemble(grantedTypeService.get(jarandClient.getClientId())));
    }

    public List<JarandClientResource> assemble(List<JarandClient> jarandClients) {
        return jarandClients.stream().map(this::assemble).toList();
    }
}
