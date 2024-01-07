package servicios;

import modelo.Libro;

import java.util.List;

public interface ServiciosLibro {

    void addLibro(Libro libro);
    void updateLibro(Libro libro);
    void deleteLibro(int id);
    List<Libro> getLibrosAutor(int autorId);
    List<Libro> getAlll();
}
