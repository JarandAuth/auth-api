package dev.jarand.authapi.scope.rest;

import dev.jarand.authapi.scope.ScopeConnectionService;
import dev.jarand.authapi.scope.ScopeService;
import dev.jarand.authapi.scope.rest.assembler.ScopeAssembler;
import dev.jarand.authapi.scope.rest.assembler.ScopeConnectionAssembler;
import dev.jarand.authapi.scope.rest.assembler.ScopeConnectionResourceAssembler;
import dev.jarand.authapi.scope.rest.assembler.ScopeResourceAssembler;
import dev.jarand.authapi.scope.rest.resource.CreateScopeConnectionResource;
import dev.jarand.authapi.scope.rest.resource.CreateScopeResource;
import dev.jarand.authapi.scope.rest.resource.ScopeConnectionResource;
import dev.jarand.authapi.scope.rest.resource.ScopeResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("scope")
public class ScopeController {

    private final ScopeService scopeService;
    private final ScopeConnectionService scopeConnectionService;
    private final ScopeAssembler scopeAssembler;
    private final ScopeConnectionAssembler scopeConnectionAssembler;
    private final ScopeResourceAssembler scopeResourceAssembler;
    private final ScopeConnectionResourceAssembler scopeConnectionResourceAssembler;

    public ScopeController(ScopeService scopeService,
                           ScopeConnectionService scopeConnectionService,
                           ScopeAssembler scopeAssembler,
                           ScopeConnectionAssembler scopeConnectionAssembler,
                           ScopeResourceAssembler scopeResourceAssembler,
                           ScopeConnectionResourceAssembler scopeConnectionResourceAssembler) {
        this.scopeService = scopeService;
        this.scopeConnectionService = scopeConnectionService;
        this.scopeAssembler = scopeAssembler;
        this.scopeConnectionAssembler = scopeConnectionAssembler;
        this.scopeResourceAssembler = scopeResourceAssembler;
        this.scopeConnectionResourceAssembler = scopeConnectionResourceAssembler;
    }

    @PostMapping
    public ResponseEntity<ScopeResource> createScope(@Valid @RequestBody CreateScopeResource resource) {
        final var scope = scopeAssembler.assembleNew(resource);
        scopeService.create(scope);
        return ResponseEntity.ok(scopeResourceAssembler.assemble(scope));
    }

    @GetMapping
    public ResponseEntity<List<ScopeResource>> getScopes() {
        return ResponseEntity.ok(scopeResourceAssembler.assemble(scopeService.getScopes()));
    }

    @GetMapping("{id}")
    public ResponseEntity<ScopeResource> getScope(@PathVariable String id) {
        return scopeService.getScope(id).map(scopeResourceAssembler::assemble).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("{id}/connection")
    public ResponseEntity<ScopeConnectionResource> createScopeConnection(@PathVariable String id, @Valid @RequestBody CreateScopeConnectionResource resource) {
        final var scopeConnection = scopeConnectionAssembler.assembleNew(id, resource);
        scopeConnectionService.create(scopeConnection);
        return ResponseEntity.ok(scopeConnectionResourceAssembler.assemble(scopeConnection));
    }

    @GetMapping("{id}/connection")
    public ResponseEntity<List<ScopeConnectionResource>> getScopeConnections(@PathVariable String id) {
        return ResponseEntity.ok(scopeConnectionResourceAssembler.assemble(scopeConnectionService.get(id)));
    }

    @GetMapping("{id}/connection/{clientId}")
    public ResponseEntity<ScopeConnectionResource> getScopeConnection(@PathVariable String id, @PathVariable String clientId) {
        return scopeConnectionService.get(id, clientId)
                .map(scopeConnectionResourceAssembler::assemble)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
