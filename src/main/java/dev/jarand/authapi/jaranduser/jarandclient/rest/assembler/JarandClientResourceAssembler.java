package dev.jarand.authapi.jaranduser.jarandclient.rest.assembler;

import dev.jarand.authapi.jaranduser.jarandclient.domain.JarandClient;
import dev.jarand.authapi.jaranduser.jarandclient.rest.resource.JarandClientResource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JarandClientResourceAssembler {

    public JarandClientResource assemble(JarandClient jarandClient) {
        return new JarandClientResource(
                jarandClient.getId().toString(),
                jarandClient.getClientId(),
                jarandClient.getClientSecret(),
                jarandClient.getOwnerId().toString(),
                jarandClient.getTimeOfCreation().toString());
    }

    public List<JarandClientResource> assemble(List<JarandClient> jarandClients) {
        return jarandClients.stream().map(this::assemble).toList();
    }
}
