package servicios.impl;

import dao.Utils;
import jakarta.di.KeyProvider;
import dao.DaoCredentials;
import dao.HasheoContrasenyas;
import dao.MandarMail;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import jakarta.inject.Inject;
import jakarta.mail.MessagingException;
import modelo.Credentials;
import modelo.exceptions.Exception401;
import servicios.ServiciosCredentials;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;

public class ServiciosCredentialsImpl implements ServiciosCredentials {
    private final KeyProvider keyProvider;

    private final DaoCredentials daoCredentials;
    private final HasheoContrasenyas hasheoContrasenyas;
    private final MandarMail mandarMail;

    @Inject
    public ServiciosCredentialsImpl(KeyProvider keyProvider, DaoCredentials daoCredentials, HasheoContrasenyas hasheoContrasenyas, MandarMail mandarMail) {
        this.keyProvider = keyProvider;
        this.daoCredentials = daoCredentials;
        this.hasheoContrasenyas = hasheoContrasenyas;
        this.mandarMail = mandarMail;
    }

    @Override
    public Credentials addCredentials(Credentials credentials) {

        String codigo = Utils.randomBytes();
        credentials.setCodigoActivacion(codigo);
        credentials.setPassword(hasheoContrasenyas.hashPassword(credentials.getPassword()));
        daoCredentials.addCredentials(credentials);

        try {

            String cuerpoCorreo = ServiceConstantes.BIENVENIDO_NUEVO_USUARIO_BR_BR;
            cuerpoCorreo += ServiceConstantes.PARA_ACTIVAR_TU_CUENTA_HAZ_CLIC_EN_EL_SIGUIENTE_ENLACE_BR;
            cuerpoCorreo += ServiceConstantes.A_HREF_HTTP_LOCALHOST_8080_PSP_JWT_1_0_SNAPSHOT_API_ACTIVAR_CUENTA_CODIGO + codigo + ServiceConstantes.PINCHA_AQUI_A;
            mandarMail.generateAndSendEmail(credentials.getEmail(), cuerpoCorreo, ServiceConstantes.CORREO_DE_BIENVENIDA);
        } catch (MessagingException e) {
            throw new Exception401(ServiceConstantes.ERROR_AL_ENVIAR_EL_CORREO);

        }
        return credentials;
    }

    @Override
    public void update(Credentials credentials) {
        daoCredentials.update(credentials);
    }

    @Override
    public Credentials getByCodigoActivacion(String id) {

        Credentials result = daoCredentials.getByCodigoActivacion(id);
        if (result != null) {
            if (Duration.between(result.getFechaActivacion(), LocalDateTime.now()).getSeconds() >= 300) {
                throw new Exception401(ServiceConstantes.TIEMPO_DE_ACTIVACION_EXPIRADO);
            } else {
                result.setActivado(true);
                daoCredentials.update(result);
                return result;
            }
        } else {
            throw new Exception401(ServiceConstantes.CODIGO_DE_ACTIVACION_NO_VALIDO);
        }

    }

    // http://localhost:8080/PSP_JWT-1.0-SNAPSHOT/api/credentials/login?user=pabsermat@gmail.com&password=1234565675785858566548648645858548548458
    public Credentials doLogin(String email, String password) {
        Credentials credentials = daoCredentials.getByEmail(email);
        if (credentials.getTemporalPassword() != null) {
            if (verifyPassword(password, credentials.getTemporalPassword())) {
                credentials.setAccessToken(generateToken(email));
                credentials.setRefreshToken(generateRefreshToken(email));
                daoCredentials.updateAccessToken(credentials);
                daoCredentials.updateRefreshToken(credentials);
                return credentials;
            }
        } else {
            if (verifyPassword(password, credentials.getPassword())) {
                credentials.setAccessToken(generateToken(email));
                credentials.setRefreshToken(generateRefreshToken(email));
                daoCredentials.updateAccessToken(credentials);
                daoCredentials.updateRefreshToken(credentials);
                return credentials;
            }
        }
        return null;
    }

    public Credentials validate(String accessToken) {
        Credentials credentials;
        credentials = daoCredentials.getByAccessToken(accessToken);
        credentials.setAccessToken(credentials.getAccessToken());
        if (validateToken(credentials.getAccessToken())) {
            return credentials;
        } else throw new Exception401(ServiceConstantes.TOKEN_NO_VALIDO);
    }

    @Override
    public void cambiarContrasenya(Credentials credentials) {
        credentials.setPassword(hasheoContrasenyas.hashPassword(credentials.getPassword()));
        daoCredentials.cambiarContrasenya(credentials);

    }

    @Override
    public void cambiarCodigoActivacion(String email) {
        Credentials credentials = new Credentials();
        credentials.setEmail(email);
        String codigo = Utils.randomBytes();
        credentials.setCodigoActivacion(codigo);
        daoCredentials.cambiarCodigoActivacion(credentials);
        try {

            String cuerpoCorreo = ServiceConstantes.BIENVENIDO_NUEVO_USUARIO_BR_BR;
            cuerpoCorreo += ServiceConstantes.PARA_ACTIVAR_TU_CUENTA_HAZ_CLIC_EN_EL_SIGUIENTE_ENLACE_BR;
            cuerpoCorreo += ServiceConstantes.A_HREF_HTTP_LOCALHOST_8080_PSP_JWT_1_0_SNAPSHOT_API_ACTIVAR_CUENTA_CODIGO + codigo + ServiceConstantes.PINCHA_AQUI_A;
            mandarMail.generateAndSendEmail(credentials.getEmail(), cuerpoCorreo, ServiceConstantes.CORREO_DE_BIENVENIDA);
        } catch (MessagingException e) {
            throw new Exception401(ServiceConstantes.ERROR_AL_ENVIAR_EL_CORREO);
        }
    }

    private boolean validateToken(String accessToken) {
        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(keyProvider.generatePrivateKey())
                    .build()
                    .parseClaimsJws(accessToken);

            long expirationMillis = claimsJws.getBody().getExpiration().getTime();
            return System.currentTimeMillis() < expirationMillis;

        } catch (ExpiredJwtException e) {
            throw new Exception401(ServiceConstantes.TOKEN_EXPIRADO);
        }
    }


    public boolean verifyPassword(String providedPassword, String storedHash) {
        Argon2 argon2 = Argon2Factory.create();
        try {
            return argon2.verify(storedHash, providedPassword.toCharArray());
        } finally {
            argon2.wipeArray(providedPassword.toCharArray());
        }
    }

    @Override
    public Credentials forgotPassword(String email) {
        Credentials credentials = new Credentials();
        credentials.setEmail(email);
        String contrasenya = Utils.randomBytes();
        credentials.setTemporalPassword(hasheoContrasenyas.hashPassword(contrasenya));
        daoCredentials.forgotPassword(credentials);
        try {
            String cuerpoCorreo = ServiceConstantes.RECUPERACION_DE_CONTRASENYA_BR_BR;
            cuerpoCorreo += ServiceConstantes.ESTA_ES_TU_NUEVA_CONTRASENYA + contrasenya + ServiceConstantes.LA_CONTRASENA_TENDRA_SOLO_UN_USO_LA_PROXIMA_VEZ_QUE_INICIES_SESION_TENDRAS_QUE_CAMBIARLA_BR;
            mandarMail.generateAndSendEmail(credentials.getEmail(), cuerpoCorreo, ServiceConstantes.RECUPERACION_DE_CONTRASENYA);
        } catch (MessagingException e) {
            throw new Exception401(ServiceConstantes.ERROR_AL_ENVIAR_EL_CORREO);

        }
        return credentials;
    }

    public String generateToken(String email) {
        Credentials credentials = daoCredentials.getByEmail(email);
        return Jwts.builder()
                .setSubject(credentials.getEmail())
                .claim(ServiceConstantes.ROL, credentials.getRol())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 300000))  // 300 seconds
                .signWith(keyProvider.generatePrivateKey())
                .compact();

    }

    public String generateRefreshToken(String email) {
        Credentials credentials = daoCredentials.getByEmail(email);
        return Jwts.builder()
                .setSubject(credentials.getEmail())
                .claim(ServiceConstantes.ROL, credentials.getRol())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 60000000))
                .signWith(keyProvider.generatePrivateKey())
                .compact();
    }

    public String refreshToken(String refreshToken) {
        Credentials credentials = daoCredentials.getByRefreshToken(refreshToken);
        if (credentials != null) {
            credentials.setAccessToken(generateToken(credentials.getEmail()));
            daoCredentials.updateAccessToken(credentials);
            return credentials.getAccessToken();
        }
        return null;
    }
}
