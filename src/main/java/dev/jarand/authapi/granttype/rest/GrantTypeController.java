package dev.jarand.authapi.granttype.rest;

import dev.jarand.authapi.granttype.GrantTypeService;
import dev.jarand.authapi.granttype.assembler.GrantTypeAssembler;
import dev.jarand.authapi.granttype.assembler.GrantTypeResourceAssembler;
import dev.jarand.authapi.granttype.resource.CreateGrantTypeResource;
import dev.jarand.authapi.granttype.resource.GrantTypeResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("grant-type")
public class GrantTypeController {

    private final GrantTypeService service;
    private final GrantTypeAssembler assembler;
    private final GrantTypeResourceAssembler resourceAssembler;

    public GrantTypeController(GrantTypeService service, GrantTypeAssembler assembler, GrantTypeResourceAssembler resourceAssembler) {
        this.service = service;
        this.assembler = assembler;
        this.resourceAssembler = resourceAssembler;
    }

    @PostMapping
    public ResponseEntity<GrantTypeResource> createGrantType(@Valid @RequestBody CreateGrantTypeResource resource) {
        final var grantType = assembler.assembleNew(resource);
        service.create(grantType);
        return ResponseEntity.ok(resourceAssembler.assemble(grantType));
    }

    @GetMapping
    public ResponseEntity<List<GrantTypeResource>> getGrantTypes() {
        return ResponseEntity.ok(resourceAssembler.assemble(service.get()));
    }

    @GetMapping("{grantType}")
    public ResponseEntity<GrantTypeResource> getGrantType(@PathVariable String grantType) {
        return service.get(grantType).map(resourceAssembler::assemble).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}
