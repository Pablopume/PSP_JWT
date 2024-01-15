package servicios.impl;

import dao.DaoLibros;
import jakarta.inject.Inject;
import modelo.Order;
import servicios.ServiciosLibro;

import java.util.List;

public class ServiciosLibrosImpl implements ServiciosLibro {
private final DaoLibros daoLibros;
    @Inject
    public ServiciosLibrosImpl(DaoLibros daoLibros) {
        this.daoLibros = daoLibros;
    }

    @Override
    public void addLibro(Order order) {
        daoLibros.addLibro(order);
    }

    @Override
    public void updateLibro(Order order) {
        daoLibros.updateLibro(order);
    }

    @Override
    public void deleteLibro(int id) {
        daoLibros.deleteLibro(id);
    }

    @Override
    public List<Order> getLibrosAutor(int autorId) {
        return daoLibros.getLibrosAutor(autorId);
    }

    @Override
    public List<Order> getAlll() {
        return daoLibros.getAlll();
    }

}
