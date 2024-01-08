package servicios;

import modelo.Credentials;

public interface ServiciosCredentials {

    Credentials addCredentials(Credentials credentials);
    void update(Credentials credentials);
    Credentials getByCodigoActivacion(String id);
    Credentials forgotPassword(String email);
    boolean doLogin(String email, String password);
    String refreshToken(String refreshToken);
}
