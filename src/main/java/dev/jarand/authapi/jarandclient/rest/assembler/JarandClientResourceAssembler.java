package dev.jarand.authapi.jarandclient.rest.assembler;

import dev.jarand.authapi.jarandclient.domain.JarandClient;
import dev.jarand.authapi.jarandclient.rest.resource.JarandClientResource;
import org.springframework.stereotype.Component;

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
}
