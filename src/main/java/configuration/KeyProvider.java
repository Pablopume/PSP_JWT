package configuration;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Produces;

import java.security.Key;

public class KeyProvider {
    @Singleton
    @Produces
    public Key generatePrivateKey() {
       return Keys.secretKeyFor(SignatureAlgorithm.HS512);
    }
}
