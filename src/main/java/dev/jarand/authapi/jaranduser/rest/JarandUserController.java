package dev.jarand.authapi.jaranduser.rest;

import dev.jarand.authapi.jaranduser.JarandUserService;
import dev.jarand.authapi.jaranduser.jarandclient.JarandClientService;
import dev.jarand.authapi.jaranduser.rest.assembler.JarandUserAssembler;
import dev.jarand.authapi.jaranduser.rest.assembler.JarandUserResourceAssembler;
import dev.jarand.authapi.jaranduser.rest.resource.CreateJarandUserResource;
import dev.jarand.authapi.jaranduser.rest.resource.JarandUserResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("jarand-user")
public class JarandUserController {

    private final JarandUserService service;
    private final JarandUserAssembler assembler;
    private final JarandUserResourceAssembler resourceAssembler;
    private final JarandClientService jarandClientService;

    public JarandUserController(JarandUserService service,
                                JarandUserAssembler assembler,
                                JarandUserResourceAssembler resourceAssembler,
                                JarandClientService jarandClientService) {
        this.service = service;
        this.assembler = assembler;
        this.resourceAssembler = resourceAssembler;
        this.jarandClientService = jarandClientService;
    }

    @PostMapping
    public ResponseEntity<JarandUserResource> createUser(@Valid @RequestBody CreateJarandUserResource resource) {
        final var jarandUser = assembler.assembleNew(resource);
        service.createUser(jarandUser);
        final var jarandClients = jarandClientService.getClients(jarandUser.getId());
        return ResponseEntity.ok(resourceAssembler.assemble(jarandUser, jarandClients));
    }
}
