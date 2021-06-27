package dev.jarand.authapi.tokendata.rest;

import dev.jarand.authapi.tokendata.TokenDataService;
import dev.jarand.authapi.tokendata.assembler.TokenDataResourceAssembler;
import dev.jarand.authapi.tokendata.resource.TokenDataResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("token-data")
public class TokenDataController {

    private final TokenDataService service;
    private final TokenDataResourceAssembler resourceAssembler;

    public TokenDataController(TokenDataService service, TokenDataResourceAssembler resourceAssembler) {
        this.service = service;
        this.resourceAssembler = resourceAssembler;
    }

    @GetMapping
    public ResponseEntity<TokenDataResource> getTokenData() {
        final var tokenData = service.getTokenData();
        return ResponseEntity.ok(resourceAssembler.assemble(tokenData));
    }
}
