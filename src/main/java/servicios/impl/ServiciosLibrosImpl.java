package servicios.impl;

import dao.DaoLibros;
import jakarta.inject.Inject;
import modelo.Libro;
import servicios.ServiciosLibro;

import java.util.List;

public class ServiciosLibrosImpl implements ServiciosLibro {
private final DaoLibros daoLibros;
    @Inject
    public ServiciosLibrosImpl(DaoLibros daoLibros) {
        this.daoLibros = daoLibros;
    }

    @Override
    public void addLibro(Libro libro) {
        daoLibros.addLibro(libro);
    }

    @Override
    public void updateLibro(Libro libro) {
        daoLibros.updateLibro(libro);
    }

    @Override
    public void deleteLibro(int id) {
        daoLibros.deleteLibro(id);
    }

    @Override
    public List<Libro> getLibrosAutor(int autorId) {
        return daoLibros.getLibrosAutor(autorId);
    }

    @Override
    public List<Libro> getAlll() {
        return daoLibros.getAlll();
    }

}
