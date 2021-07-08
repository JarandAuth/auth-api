package dev.jarand.authapi.jarandclient.rest;

import dev.jarand.authapi.jarandclient.JarandClientService;
import dev.jarand.authapi.jarandclient.rest.assembler.JarandClientResourceAssembler;
import dev.jarand.authapi.jarandclient.rest.assembler.SecretClientAssembler;
import dev.jarand.authapi.jarandclient.rest.resource.CreateJarandClientResource;
import dev.jarand.authapi.jarandclient.rest.resource.JarandClientResource;
import dev.jarand.authapi.security.SubjectProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("jarand-client")
public class JarandClientController {

    private final JarandClientService service;
    private final SecretClientAssembler assembler;
    private final JarandClientResourceAssembler resourceAssembler;
    private final SubjectProvider subjectProvider;

    public JarandClientController(JarandClientService service,
                                  SecretClientAssembler assembler,
                                  JarandClientResourceAssembler resourceAssembler,
                                  SubjectProvider subjectProvider) {
        this.service = service;
        this.assembler = assembler;
        this.resourceAssembler = resourceAssembler;
        this.subjectProvider = subjectProvider;
    }

    @PostMapping
    public ResponseEntity<?> createClient(@Valid @RequestBody CreateJarandClientResource resource) {
        final var clientId = subjectProvider.getSubject();
        final var client = service.getClient(clientId);
        if (client.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        final var ownerId = client.get().getOwnerId();
        final var secretClient = assembler.assembleNew(resource, ownerId);
        service.createSecretClient(secretClient);
        return ResponseEntity.ok(resourceAssembler.assemble(secretClient));
    }

    @GetMapping
    public ResponseEntity<List<JarandClientResource>> getClients(@RequestParam("ownerId") Optional<UUID> ownerId) {
        return ownerId.map(uuid -> ResponseEntity.ok(resourceAssembler.assemble(service.getClients(uuid))))
                .orElseGet(() -> ResponseEntity.ok(resourceAssembler.assemble(service.getClients())));
    }

    @GetMapping("{clientId}")
    public ResponseEntity<JarandClientResource> getClient(@PathVariable String clientId) {
        return service.getClient(clientId).map(resourceAssembler::assemble).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}
