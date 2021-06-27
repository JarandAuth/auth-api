package dev.jarand.authapi.tokendata;

import dev.jarand.authapi.tokendata.domain.TokenData;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class TokenDataService {

    public TokenData getTokenData() {
        final var authentication = SecurityContextHolder.getContext().getAuthentication();
        final var subject = (String) authentication.getPrincipal();
        final var scopes = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        return new TokenData(subject, scopes);
    }
}
