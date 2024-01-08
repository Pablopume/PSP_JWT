package security;


import dao.TokenCredentials;
import jakarta.inject.Inject;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStore;
import modelo.Credentials;
import servicios.ServiciosCredentials;

import java.util.Collections;

import static jakarta.security.enterprise.identitystore.CredentialValidationResult.INVALID_RESULT;

public class InMemoryIdentityStore implements IdentityStore {
    private final ServiciosCredentials credentialsService;

    @Inject
    public InMemoryIdentityStore(ServiciosCredentials credentialsService) {
        this.credentialsService = credentialsService;
    }

    @Override
    public int priority() {
        return 10;
    }

    @Override
    public CredentialValidationResult validate(Credential credential) {
        TokenCredentials tokens = (TokenCredentials) credential;
        Credentials credentials = new Credentials();
        if (tokens.getAccessToken() != null) {
            credentials.setAccessToken(tokens.getAccessToken());
            try {
                if (credentialsService.doLogin(credentials.getEmail(), credentials.getPassword())) {
                    return new CredentialValidationResult
                            (credentials.getEmail(), Collections.singleton(credentials.getRol()));
                } else return INVALID_RESULT;
            } catch (Exception e){
                return CredentialValidationResult.NOT_VALIDATED_RESULT;
            }

        }
        return INVALID_RESULT;
    }

}