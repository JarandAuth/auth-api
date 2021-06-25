package dev.jarand.authapi.jaranduser.jarandclient.rest;

import dev.jarand.authapi.jaranduser.JarandUserService;
import dev.jarand.authapi.jaranduser.jarandclient.JarandClientService;
import dev.jarand.authapi.jaranduser.jarandclient.rest.assembler.JarandClientResourceAssembler;
import dev.jarand.authapi.jaranduser.jarandclient.rest.assembler.SecretClientAssembler;
import dev.jarand.authapi.jaranduser.jarandclient.rest.resource.CreateJarandClientResource;
import dev.jarand.authapi.jaranduser.jarandclient.rest.resource.ErrorResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/jarand-user/{id}/jarand-client")
public class JarandClientController {

    private final JarandClientService service;
    private final SecretClientAssembler assembler;
    private final JarandClientResourceAssembler resourceAssembler;
    private final JarandUserService jarandUserService;

    public JarandClientController(JarandClientService service, SecretClientAssembler assembler, JarandClientResourceAssembler resourceAssembler, JarandUserService jarandUserService) {
        this.service = service;
        this.assembler = assembler;
        this.resourceAssembler = resourceAssembler;
        this.jarandUserService = jarandUserService;
    }

    @PostMapping
    public ResponseEntity<?> createClient(@PathVariable String id, @Valid @RequestBody CreateJarandClientResource resource) {
        final var ownerId = UUID.fromString(id);
        final var secretClient = assembler.assembleNew(resource, ownerId);
        if (jarandUserService.getUser(ownerId).isEmpty()) {
            return ResponseEntity.badRequest().body(new ErrorResource("User with id: " + ownerId + " does not exist"));
        }
        service.createSecretClient(secretClient);
        return ResponseEntity.ok(resourceAssembler.assemble(secretClient));
    }
}
