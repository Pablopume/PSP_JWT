package dao.impl;

import dao.DBConnectionPool;
import dao.DaoAutores;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import modelo.Autor;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Log4j2
public class DaoAutoresImpl implements DaoAutores {
    private final DBConnectionPool dbConnectionPool;

    @Inject
    public DaoAutoresImpl(DBConnectionPool dbConnectionPool) {
        this.dbConnectionPool = dbConnectionPool;
    }

    public void add(Autor autor) {
        try (Connection connection = dbConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Autores VALUES (?, ?, ?, ?, ?,?,?,?,?)")) {
            preparedStatement.setInt(1, autor.getId());
            preparedStatement.setString(2, autor.getNombre());
            preparedStatement.setString(3, autor.getPais());
            preparedStatement.setDate(4, Date.valueOf(autor.getFechaNacimiento()));
            preparedStatement.setInt(5, 0);
            preparedStatement.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setString(7, autor.getCodigoActivacion());
            preparedStatement.setString(8, autor.getEmail());
            preparedStatement.setString(9, autor.getPassword());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    public void update(Autor autor) {
        int activado = 0;
        if (autor.isActivado()) {
            activado = 1;
        }
        try (Connection connection = dbConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Autores SET nombre = ?, PaisOrigen = ?, fechaNacimiento = ?, activado = ?, fechaActivacion = ?, codigoActivacion = ?, Mail = ?, Contraseña = ? WHERE AutorID = ?")) {
            preparedStatement.setString(1, autor.getNombre());
            preparedStatement.setString(2, autor.getPais());
            preparedStatement.setDate(3, Date.valueOf(autor.getFechaNacimiento()));
            preparedStatement.setInt(4, activado);
            preparedStatement.setDate(5, Date.valueOf(LocalDate.now()));
            preparedStatement.setString(6, autor.getCodigoActivacion());
            preparedStatement.setString(7, autor.getEmail());
            preparedStatement.setString(8, autor.getPassword());
            preparedStatement.setInt(9, autor.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    public Autor getByCodigoActivacion(String id) {
        Autor autor = null;
        try (Connection connection = dbConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Autores WHERE CodigoActivacion = ?")) {
            preparedStatement.setString(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            // Check if there are any rows in the result set
            if (rs.next()) {
                autor = new Autor(
                        rs.getInt("AutorID"),
                        rs.getString("Nombre"),
                        rs.getString("PaisOrigen"),
                        rs.getDate("FechaNacimiento").toLocalDate(),
                        rs.getInt("Activado") == 1,
                        rs.getTimestamp("FechaActivacion").toLocalDateTime(),
                        rs.getString("CodigoActivacion"),
                        rs.getString("Mail"),
                        rs.getString("Contraseña"));
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return autor;
    }

    public Autor getByEmail(String email) {
        Autor autor = null;
        try (Connection connection = dbConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Autores WHERE Mail = ?")) {
            preparedStatement.setString(1, email);
            ResultSet rs = preparedStatement.executeQuery();

            // Check if there are any rows in the result set
            if (rs.next()) {
                autor = new Autor(
                        rs.getInt("AutorID"),
                        rs.getString("Nombre"),
                        rs.getString("PaisOrigen"),
                        rs.getDate("FechaNacimiento").toLocalDate(),
                        rs.getInt("Activado") == 1,
                        rs.getTimestamp("FechaActivacion").toLocalDateTime(),
                        rs.getString("CodigoActivacion"),
                        rs.getString("Mail"),
                        rs.getString("Contraseña"));
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return autor;
    }
    public List<Autor> getAll(){
        List<Autor> autores = null;
        try (Connection connection = dbConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Autores")) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                autores.add(new Autor(
                        rs.getInt("AutorID"),
                        rs.getString("Nombre"),
                        rs.getString("PaisOrigen"),
                        rs.getDate("FechaNacimiento").toLocalDate(),
                        rs.getInt("Activado") == 1,
                        rs.getTimestamp("FechaActivacion").toLocalDateTime(),
                        rs.getString("CodigoActivacion"),
                        rs.getString("Mail"),
                        rs.getString("Contraseña")));
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return autores;
    }

}
