package dev.jarand.authapi.scopeconnection.rest;

import dev.jarand.authapi.scopeconnection.ScopeConnectionService;
import dev.jarand.authapi.scopeconnection.assembler.ScopeConnectionAssembler;
import dev.jarand.authapi.scopeconnection.assembler.ScopeConnectionResourceAssembler;
import dev.jarand.authapi.scopeconnection.resource.CreateScopeConnectionResource;
import dev.jarand.authapi.scopeconnection.resource.ScopeConnectionResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("scope-connection")
public class ScopeConnectionController {

    private final ScopeConnectionService service;
    private final ScopeConnectionAssembler assembler;
    private final ScopeConnectionResourceAssembler resourceAssembler;

    public ScopeConnectionController(ScopeConnectionService service, ScopeConnectionAssembler assembler, ScopeConnectionResourceAssembler resourceAssembler) {
        this.service = service;
        this.assembler = assembler;
        this.resourceAssembler = resourceAssembler;
    }

    @PostMapping
    public ResponseEntity<ScopeConnectionResource> create(@Valid @RequestBody CreateScopeConnectionResource resource) {
        final var scopeConnection = assembler.assembleNew(resource);
        service.create(scopeConnection);
        return ResponseEntity.ok(resourceAssembler.assemble(scopeConnection));
    }

    @GetMapping
    public ResponseEntity<?> get(@RequestParam("scopeId") Optional<String> scopeId, @RequestParam("clientId") Optional<String> clientId) {
        if (scopeId.isPresent() && clientId.isPresent()) {
            return service.get(scopeId.get(), clientId.get()).map(resourceAssembler::assemble).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
        } else if (scopeId.isPresent()) {
            return ResponseEntity.ok(resourceAssembler.assemble(service.getByScopeId(scopeId.get())));
        } else if (clientId.isPresent()) {
            return ResponseEntity.ok(resourceAssembler.assemble(service.getByClientId(clientId.get())));
        }
        return ResponseEntity.ok(resourceAssembler.assemble(service.get()));
    }
}
