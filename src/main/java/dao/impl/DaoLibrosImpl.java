package dao.impl;

import dao.DBConnectionPool;
import dao.DaoLibros;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import modelo.Libro;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class DaoLibrosImpl implements DaoLibros {
    private final DBConnectionPool dbConnectionPool;
    @Inject
    public DaoLibrosImpl(DBConnectionPool dbConnectionPool) {
        this.dbConnectionPool = dbConnectionPool;
    }
    public List<Libro> getAlll(){
        List<Libro> libros = new ArrayList<>();
        try (Connection connection = dbConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Libros")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                libros.add(new Libro(resultSet.getInt("LibroID"), resultSet.getString("Titulo"), resultSet.getInt("AnioPublicacion"), resultSet.getInt("AutorID")));
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return libros;
    }
    public void addLibro(Libro libro) {
        try (Connection connection = dbConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Libros VALUES (?, ?, ?, ?)")) {
            preparedStatement.setInt(1, libro.getId());
            preparedStatement.setString(2, libro.getTitulo());
            preparedStatement.setInt(3, libro.getAnyo());
            preparedStatement.setInt(4, libro.getAutorId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }
    public void updateLibro(Libro libro) {
        try (Connection connection = dbConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Libros SET titulo = ?, anyo = ?, autorId = ? WHERE id = ?")) {
            preparedStatement.setString(1, libro.getTitulo());
            preparedStatement.setInt(2, libro.getAnyo());
            preparedStatement.setInt(3, libro.getAutorId());
            preparedStatement.setInt(4, libro.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }
    public void deleteLibro(int id) {
        try (Connection connection = dbConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Libros WHERE id = ?")) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }
    public List<Libro> getLibrosAutor(int autorId) {
        List<Libro> libros = new ArrayList<>();
        try (Connection connection = dbConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Libros WHERE autorId = ?")) {
            preparedStatement.setInt(1, autorId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                libros.add(new Libro(resultSet.getInt("LibroID"), resultSet.getString("Titulo"), resultSet.getInt("AnioPublicacion"), resultSet.getInt("AutorID")));
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return libros;
    }
}
