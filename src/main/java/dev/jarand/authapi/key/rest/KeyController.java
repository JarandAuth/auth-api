package dev.jarand.authapi.key.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.PublicKey;
import java.util.Base64;

@RestController
@RequestMapping("key")
public class KeyController {

    private final String base64EncodedPublicKey;

    public KeyController(PublicKey publicKey) {
        this.base64EncodedPublicKey = Base64.getEncoder().encodeToString(publicKey.getEncoded());
    }

    @GetMapping("public")
    public ResponseEntity<String> getPublicKey() {
        return ResponseEntity.ok(base64EncodedPublicKey);
    }
}
