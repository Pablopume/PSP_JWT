package servicios.impl;

import dao.DaoCredentials;
import dao.HasheoContrasenyas;
import dao.MandarMail;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.inject.Inject;
import jakarta.mail.MessagingException;
import modelo.Credentials;
import servicios.ServiciosCredentials;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.UUID;

public class ServiciosCredentialsImpl implements ServiciosCredentials {
    private final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    private final DaoCredentials daoCredentials;
    private final HasheoContrasenyas hasheoContrasenyas;

    @Inject
    public ServiciosCredentialsImpl(DaoCredentials daoCredentials, HasheoContrasenyas hasheoContrasenyas) {
        this.daoCredentials = daoCredentials;
        this.hasheoContrasenyas = hasheoContrasenyas;
    }

    @Override
    public Credentials addCredentials(Credentials credentials) {
        UUID uuid = UUID.randomUUID();
        credentials.setCodigoActivacion(uuid.toString());
        credentials.setPassword(hasheoContrasenyas.hashPassword(credentials.getPassword()));
        daoCredentials.addCredentials(credentials);
        MandarMail mandarMail = new MandarMail();
        try {
            String codigoActivacion = credentials.getCodigoActivacion();
            String cuerpoCorreo = "¡Bienvenido nuevo usuario!<br><br>";
            cuerpoCorreo += "Para activar tu cuenta, haz clic en el siguiente enlace:<br>";
            cuerpoCorreo += "<a href='http://localhost:8080/PSP_JWT-1.0-SNAPSHOT/api/activar-cuenta?codigo=" + codigoActivacion + "'>Pincha aquí</a>";
            mandarMail.generateAndSendEmail(credentials.getEmail(), cuerpoCorreo, "Correo de bienvenida");
        } catch (MessagingException e) {


        }
        return credentials;
    }

    @Override
    public void update(Credentials credentials) {
        daoCredentials.update(credentials);
    }

    @Override
    public Credentials getByCodigoActivacion(String id) {
        return daoCredentials.getByCodigoActivacion(id);

    }
// http://localhost:8080/PSP_JWT-1.0-SNAPSHOT/api/credentials/login?user=pabsermat@gmail.com&password=1234565675785858566548648645858548548458
    public boolean doLogin(String email, String password) {
        Credentials credentials = daoCredentials.getByEmail(email);
        if (verifyPassword(password, credentials.getPassword())) {
            credentials.setAccessToken(generateToken(email));
            credentials.setRefreshToken(generateRefreshToken(email));
            daoCredentials.updateAccessToken(credentials);
            daoCredentials.updateRefreshToken(credentials);
            return true;
        }
        return false;
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
        Credentials credentials=new Credentials();
        credentials.setEmail(email);
        UUID uuid = UUID.randomUUID();
        credentials.setTemporalPassword(hasheoContrasenyas.hashPassword(uuid.toString()));
        daoCredentials.forgotPassword(credentials);
        MandarMail mandarMail = new MandarMail();
        try {
            String cuerpoCorreo = "Recuperación de contraseña<br><br>";
            cuerpoCorreo += "Esta es tu nueva contraseña: " + uuid + ", la contraseña tendrá solo un uso, la próxima vez que inicies sesión tendrás que cambiarla<br>";
            mandarMail.generateAndSendEmail(credentials.getEmail(), cuerpoCorreo, "Correo de bienvenida");
        } catch (MessagingException e) {


        }
        return credentials;
    }

    public String generateToken(String email) {
        Credentials credentials = daoCredentials.getByEmail(email);
        return Jwts.builder()
                .setSubject(credentials.getEmail())
                .claim("rol", credentials.getRol())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 300))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();

    }
    public String generateRefreshToken(String email) {
        Credentials credentials = daoCredentials.getByEmail(email);
        return Jwts.builder()
                .setSubject(credentials.getEmail())
                .claim("rol", credentials.getRol())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 60000))
                .signWith(secretKey)
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
