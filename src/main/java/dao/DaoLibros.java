package dao;

import modelo.Order;

import java.util.List;

public interface DaoLibros {
    List<Order> getAlll();
    void addLibro(Order order);
    void updateLibro(Order order);
    void deleteLibro(int id);
    List<Order> getLibrosAutor(int autorId);
}
