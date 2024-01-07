package dao;

import modelo.Libro;

import java.util.List;

public interface DaoLibros {
    List<Libro> getAlll();
    void addLibro(Libro libro);
    void updateLibro(Libro libro);
    void deleteLibro(int id);
    List<Libro> getLibrosAutor(int autorId);
}
