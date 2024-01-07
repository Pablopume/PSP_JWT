package dao;

import modelo.Autor;

import java.util.List;

public interface DaoAutores {
    void add(Autor autor);

    void update(Autor autor);
    Autor getByCodigoActivacion(String id);
    List<Autor> getAll();
    Autor getByEmail(String email);

}
