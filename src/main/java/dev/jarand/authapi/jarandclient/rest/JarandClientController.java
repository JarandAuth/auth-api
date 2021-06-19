package dev.jarand.authapi.jarandclient.rest;

import dev.jarand.authapi.jarandclient.JarandClientService;
import dev.jarand.authapi.jarandclient.rest.assembler.JarandClientAssembler;
import dev.jarand.authapi.jarandclient.rest.assembler.JarandClientResourceAssembler;
import dev.jarand.authapi.jarandclient.rest.resource.CreateJarandClientResource;
import dev.jarand.authapi.jarandclient.rest.resource.ErrorResource;
import dev.jarand.authapi.jaranduser.JarandUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("jarand-client")
public class JarandClientController {

    private final JarandClientService service;
    private final JarandClientAssembler assembler;
    private final JarandClientResourceAssembler resourceAssembler;
    private final JarandUserService jarandUserService;

    public JarandClientController(JarandClientService service, JarandClientAssembler assembler, JarandClientResourceAssembler resourceAssembler, JarandUserService jarandUserService) {
        this.service = service;
        this.assembler = assembler;
        this.resourceAssembler = resourceAssembler;
        this.jarandUserService = jarandUserService;
    }

    @PostMapping
    public ResponseEntity<?> createClient(@Valid @RequestBody CreateJarandClientResource resource) {
        final var jarandClient = assembler.assembleNew(resource);
        final var ownerId = UUID.fromString(resource.getOwnerId());
        if (jarandUserService.getUser(ownerId).isEmpty()) {
            return ResponseEntity.badRequest().body(new ErrorResource("Owner with ownerId: " + ownerId + " does not exist"));
        }
        service.createClient(jarandClient);
        return ResponseEntity.ok(resourceAssembler.assemble(jarandClient));
    }
}
