package servicios.impl;

import dao.DaoAutores;
import dao.HasheoContrasenyas;
import jakarta.inject.Inject;
import jakarta.mail.MessagingException;
import dao.MandarMail;
import modelo.Autor;
import servicios.ServicesAutor;

import java.util.List;
import java.util.UUID;

public class ServicesAutorImpl implements ServicesAutor {
    private final DaoAutores daoAutores;
    private final HasheoContrasenyas hasheoContrasenyas;
    @Inject
    public ServicesAutorImpl(DaoAutores daoAutores, HasheoContrasenyas hasheoContrasenyas) {
        this.daoAutores = daoAutores;
        this.hasheoContrasenyas = hasheoContrasenyas;
    }
    public Autor add(Autor autor) {
        UUID uuid = UUID.randomUUID();
        autor.setCodigoActivacion(uuid.toString());
        autor.setPassword(hasheoContrasenyas.hashPassword(autor.getPassword()));
        daoAutores.add(autor);
        MandarMail mandarMail = new MandarMail();
        try {
            String codigoActivacion = autor.getCodigoActivacion();
            String cuerpoCorreo = "¡Bienvenido nuevo usuario!<br><br>";
            cuerpoCorreo += "Para activar tu cuenta, haz clic en el siguiente enlace:<br>";
            cuerpoCorreo += "<a href='http://localhost:8080/PSP_JWT-1.0-SNAPSHOT/api/activar-cuenta?codigo=" + codigoActivacion + "'>Pincha aquí</a>";
            mandarMail.generateAndSendEmail(autor.getEmail(),cuerpoCorreo, "Correo de bienvenida");
        } catch (MessagingException e) {


        }
        return autor;
    }
    public Autor update(Autor autor) {
        daoAutores.update(autor);
        return autor;
    }

    @Override
    public Autor getByCodigoActivacion(String id) {
        return daoAutores.getByCodigoActivacion(id);
    }

    @Override
    public List<Autor> getAll() {
        return daoAutores.getAll();
    }

    @Override
    public Autor getByEmail(String email) {
        return daoAutores.getByEmail(email);
    }
}
