package dev.jarand.authapi.grantedtype.rest;

import dev.jarand.authapi.grantedtype.GrantedTypeService;
import dev.jarand.authapi.grantedtype.assembler.GrantedTypeAssembler;
import dev.jarand.authapi.grantedtype.assembler.GrantedTypeResourceAssembler;
import dev.jarand.authapi.grantedtype.resource.CreateGrantedTypeResource;
import dev.jarand.authapi.grantedtype.resource.GrantedTypeResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("granted-type")
public class GrantedTypeController {

    private final GrantedTypeService service;
    private final GrantedTypeAssembler assembler;
    private final GrantedTypeResourceAssembler resourceAssembler;

    public GrantedTypeController(GrantedTypeService service, GrantedTypeAssembler assembler, GrantedTypeResourceAssembler resourceAssembler) {
        this.service = service;
        this.assembler = assembler;
        this.resourceAssembler = resourceAssembler;
    }

    @PostMapping
    public ResponseEntity<GrantedTypeResource> createGrantedType(@Valid @RequestBody CreateGrantedTypeResource resource) {
        final var grantedType = assembler.assembleNew(resource);
        service.create(grantedType);
        return ResponseEntity.ok(resourceAssembler.assemble(grantedType));
    }

    @GetMapping
    public ResponseEntity<List<GrantedTypeResource>> getGrantedTypes(@RequestParam("clientId") Optional<String> optionalClientId) {
        return optionalClientId.map(clientId -> ResponseEntity.ok(resourceAssembler.assemble(service.get(clientId))))
                .orElseGet(() -> ResponseEntity.ok(resourceAssembler.assemble(service.get())));
    }
}
