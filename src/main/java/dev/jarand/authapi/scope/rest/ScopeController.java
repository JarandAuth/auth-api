package dev.jarand.authapi.scope.rest;

import dev.jarand.authapi.scope.ScopeService;
import dev.jarand.authapi.scope.assembler.ScopeAssembler;
import dev.jarand.authapi.scope.assembler.ScopeResourceAssembler;
import dev.jarand.authapi.scope.resource.CreateScopeResource;
import dev.jarand.authapi.scope.resource.ScopeResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("scope")
public class ScopeController {

    private final ScopeService scopeService;
    private final ScopeAssembler scopeAssembler;
    private final ScopeResourceAssembler scopeResourceAssembler;

    public ScopeController(ScopeService scopeService, ScopeAssembler scopeAssembler, ScopeResourceAssembler scopeResourceAssembler) {
        this.scopeService = scopeService;
        this.scopeAssembler = scopeAssembler;
        this.scopeResourceAssembler = scopeResourceAssembler;
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
}
