package dev.jarand.authapi.token.rest;

import dev.jarand.authapi.token.TokenService;
import dev.jarand.authapi.token.assembler.RefreshTokenResourceAssembler;
import dev.jarand.authapi.token.resource.RefreshTokenResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("refresh-token")
public class RefreshTokenController {

    private final TokenService tokenService;
    private final RefreshTokenResourceAssembler resourceAssembler;

    public RefreshTokenController(TokenService tokenService, RefreshTokenResourceAssembler resourceAssembler) {
        this.tokenService = tokenService;
        this.resourceAssembler = resourceAssembler;
    }

    @GetMapping
    public ResponseEntity<List<RefreshTokenResource>> getRefreshTokens() {
        return ResponseEntity.ok(resourceAssembler.assemble(tokenService.getRefreshTokens()));
    }
}
