package configuration;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Produces;

import java.security.Key;
@Singleton
public class KeyProvider {
    private final Key privateKey;
    public KeyProvider() {
        // Generar la clave una vez en la inicialización
        this.privateKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    }

    @Produces
    public Key generatePrivateKey() {
        // Devolver la misma clave almacenada
        return privateKey;
    }
}
