package dev.jarand.authapi.oauth;

import dev.jarand.authapi.jarandclient.JarandClientService;
import dev.jarand.authapi.jaranduser.JarandUserService;
import dev.jarand.authapi.jaranduser.domain.JarandUser;
import dev.jarand.authapi.oauth.domain.AuthorizeCheckResult;
import dev.jarand.authapi.oauth.rest.resource.AuthorizeCheckRequest;
import dev.jarand.authapi.scope.ScopeService;
import dev.jarand.authapi.scopeconnection.ScopeConnectionService;
import dev.jarand.authapi.security.SubjectProvider;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;

@Service
public class AuthorizeService {

    private final JarandClientService clientService;
    private final SubjectProvider subjectProvider;
    private final ScopeConnectionService scopeConnectionService;
    private final ScopeService scopeService;
    private final JarandUserService userService;

    public AuthorizeService(JarandClientService clientService,
                            SubjectProvider subjectProvider,
                            ScopeConnectionService scopeConnectionService,
                            ScopeService scopeService,
                            JarandUserService userService) {
        this.clientService = clientService;
        this.subjectProvider = subjectProvider;
        this.scopeConnectionService = scopeConnectionService;
        this.scopeService = scopeService;
        this.userService = userService;
    }

    public AuthorizeCheckResult validate(AuthorizeCheckRequest request) {
        return validate(request.getResponseType(), request.getClientId(), request.getRedirectUri(), request.getScope(), request.getState());
    }

    public AuthorizeCheckResult validate(String responseType, String clientId, String redirectUri, String scope, String state) {
        final var errors = new ArrayList<String>();
        if ("code".equals(responseType)) {
            errors.add("Unknown response_type. Expected 'code'");
        }
        final var optionalClient = clientService.getClient(clientId);
        if (optionalClient.map(c -> !"SECRET".equals(c.getType())).orElse(true)) {
            errors.add("Unknown client_id. Client is wrong type");
        }
        final var subject = subjectProvider.getSubject();
        final var optionalUserClient = clientService.getClient(subject);
        if (optionalUserClient.isEmpty()) {
            errors.add("Unknown user. Client does not exist or is wrong type");
        }
        final var scopeIds = Arrays.asList(scope.split(" "));
        final var scopeConnections = scopeConnectionService.getByClientId(subject);
        if (scopeIds.size() != scopeConnections.size()) {
            errors.add("Invalid scopes. All the scopes requested does not exist or the user does not have access to them");
        }
        if (!errors.isEmpty()) {
            return new AuthorizeCheckResult(errors, null, null, null, null);
        }
        final var scopes = scopeService.getScopes(scopeIds);
        final var userClient = optionalUserClient.orElseThrow();
        final var client = optionalClient.orElseThrow();
        final var clientOwner = userService.getUser(client.getOwnerId()).map(JarandUser::getDisplayName).orElseThrow();
        return new AuthorizeCheckResult(null, userClient, client, scopes, clientOwner);
    }
}
