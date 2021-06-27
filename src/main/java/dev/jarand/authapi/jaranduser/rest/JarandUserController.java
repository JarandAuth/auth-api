package dev.jarand.authapi.jaranduser.rest;

import dev.jarand.authapi.jaranduser.JarandUserService;
import dev.jarand.authapi.jaranduser.rest.assembler.JarandUserAssembler;
import dev.jarand.authapi.jaranduser.rest.assembler.JarandUserResourceAssembler;
import dev.jarand.authapi.jaranduser.rest.resource.CreateJarandUserResource;
import dev.jarand.authapi.jaranduser.rest.resource.JarandUserResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("jarand-user")
public class JarandUserController {

    private final JarandUserService service;
    private final JarandUserAssembler assembler;
    private final JarandUserResourceAssembler resourceAssembler;

    public JarandUserController(JarandUserService service,
                                JarandUserAssembler assembler,
                                JarandUserResourceAssembler resourceAssembler) {
        this.service = service;
        this.assembler = assembler;
        this.resourceAssembler = resourceAssembler;
    }

    @PostMapping
    public ResponseEntity<JarandUserResource> createUser(@Valid @RequestBody CreateJarandUserResource resource) {
        final var jarandUser = assembler.assembleNew(resource);
        service.createUser(jarandUser, resource.getPassword());
        return ResponseEntity.ok(resourceAssembler.assemble(jarandUser));
    }

    @GetMapping
    public ResponseEntity<List<JarandUserResource>> getUsers() {
        return ResponseEntity.ok(resourceAssembler.assemble(service.getUsers()));
    }

    @GetMapping("{id}")
    public ResponseEntity<JarandUserResource> getUser(@PathVariable UUID id) {
        return service.getUser(id).map(resourceAssembler::assemble).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}
