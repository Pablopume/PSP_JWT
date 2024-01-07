package servicios;

import modelo.Autor;

import java.util.List;

public interface ServicesAutor {
    Autor add(Autor autor);

    Autor update(Autor autor);

    Autor getByCodigoActivacion(String id);
    List<Autor> getAll();
    Autor getByEmail(String email);
}
