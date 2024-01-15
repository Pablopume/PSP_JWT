package servicios;

import modelo.Order;

import java.util.List;

public interface ServiciosLibro {

    void addLibro(Order order);
    void updateLibro(Order order);
    void deleteLibro(int id);
    List<Order> getLibrosAutor(int autorId);
    List<Order> getAlll();
}
