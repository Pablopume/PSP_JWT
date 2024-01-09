package dao;

import modelo.Credentials;

public interface DaoCredentials {
    void addCredentials(Credentials credentials);
    void update(Credentials credentials);
    Credentials getByCodigoActivacion(String id);
    void forgotPassword(Credentials credentials);
    Credentials getByEmail(String email);
    void updateRefreshToken(Credentials credentials);
    void updateAccessToken(Credentials credentials);
    Credentials getByRefreshToken(String refreshToken);
    Credentials getByAccessToken(String accessToken);
}
