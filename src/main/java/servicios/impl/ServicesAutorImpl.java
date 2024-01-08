package servicios.impl;

import dao.DaoAutores;
import dao.HasheoContrasenyas;
import jakarta.inject.Inject;
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
