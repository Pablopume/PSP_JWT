package security;

import dao.TokenCredentials;
import jakarta.RestConstantes;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import jakarta.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.core.HttpHeaders;


@ApplicationScoped
public class JWTAuth implements HttpAuthenticationMechanism {
    private final InMemoryIdentityStore identity;

    @Inject
    public JWTAuth(InMemoryIdentityStore identity) {
        this.identity = identity;
    }

    @Override
    public AuthenticationStatus validateRequest(HttpServletRequest request, HttpServletResponse response, HttpMessageContext httpMessageContext) {
        String requestURL = request.getRequestURL().toString();

        if (requestURL.contains(SecurityConstantes.LOGIN) || requestURL.contains(SecurityConstantes.FORGOT_PASSWORD)
                || requestURL.contains(SecurityConstantes.REFRESH_TOKEN) || requestURL.contains(SecurityConstantes.CREDENTIALS) || requestURL.contains(SecurityConstantes.CAMBIAR_CONTRASENYA) || requestURL.contains(RestConstantes.ACTIVAR) || requestURL.contains(RestConstantes.CAMBIAR)) {
            return httpMessageContext.doNothing();
        }
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith(SecurityConstantes.BEARER)) {
            String token = authorizationHeader.substring(SecurityConstantes.BEARER.length());

            TokenCredentials tokenCredentials = new TokenCredentials(token, null);
            CredentialValidationResult validationResult = identity.validate(tokenCredentials);

            if (validationResult.getStatus() == CredentialValidationResult.Status.VALID) {
                return httpMessageContext.notifyContainerAboutLogin(validationResult);
            } else if (validationResult.getStatus() == CredentialValidationResult.Status.NOT_VALIDATED) {
                return httpMessageContext.responseUnauthorized();
            }
        }

        return httpMessageContext.responseUnauthorized();
    }


}