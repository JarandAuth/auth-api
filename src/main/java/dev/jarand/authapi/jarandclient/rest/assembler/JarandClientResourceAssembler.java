package dev.jarand.authapi.jarandclient.rest.assembler;

import dev.jarand.authapi.grantedtype.GrantedTypeService;
import dev.jarand.authapi.grantedtype.assembler.GrantedTypeResourceAssembler;
import dev.jarand.authapi.jarandclient.domain.JarandClient;
import dev.jarand.authapi.jarandclient.domain.LoginClient;
import dev.jarand.authapi.jarandclient.domain.SecretClient;
import dev.jarand.authapi.jarandclient.rest.resource.JarandClientResource;
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

    public JarandClientResource assemble(JarandClient client) {
        final var type = client.getType();
        switch (type) {
            case "SECRET" -> {
                final var secretClient = (SecretClient) client;
                return new JarandClientResource(
                        secretClient.getClientId(),
                        secretClient.getDisplayName(),
                        secretClient.getOwnerId().toString(),
                        secretClient.getTimeOfCreation().toString(),
                        secretClient.getClientSecret(),
                        null,
                        null,
                        grantedTypeResourceAssembler.assemble(grantedTypeService.get(secretClient.getClientId())));
            }
            case "LOGIN" -> {
                final var loginClient = (LoginClient) client;
                return new JarandClientResource(
                        loginClient.getClientId(),
                        loginClient.getDisplayName(),
                        loginClient.getOwnerId().toString(),
                        loginClient.getTimeOfCreation().toString(),
                        null,
                        loginClient.getUsername(),
                        loginClient.getPassword(),
                        grantedTypeResourceAssembler.assemble(grantedTypeService.get(loginClient.getClientId())));
            }
            default -> throw new IllegalStateException("Unexpected client type: " + type);
        }
    }

    public List<JarandClientResource> assemble(List<JarandClient> jarandClients) {
        return jarandClients.stream().map(this::assemble).toList();
    }
}
