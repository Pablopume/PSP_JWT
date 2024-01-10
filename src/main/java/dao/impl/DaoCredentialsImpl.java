package dao.impl;

import dao.DBConnectionPool;
import dao.DaoCredentials;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import modelo.Credentials;

import java.sql.*;
import java.time.LocalDateTime;

@Log4j2
public class DaoCredentialsImpl implements DaoCredentials {
    private final DBConnectionPool dbConnectionPool;

    @Inject
    public DaoCredentialsImpl(DBConnectionPool dbConnectionPool) {
        this.dbConnectionPool = dbConnectionPool;
    }

    public void addCredentials(Credentials credentials) {
        try (Connection connection = dbConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Credentials (email, password, activado, fechaActivacion, codigoActivacion, rol) VALUES (?, ?, ?, ?, ?, ?)");
        ) {
            preparedStatement.setString(1, credentials.getEmail());
            preparedStatement.setString(2, credentials.getPassword());
            preparedStatement.setInt(3, 0);
            preparedStatement.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setString(5, credentials.getCodigoActivacion());
            preparedStatement.setString(6, credentials.getRol());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    public void update(Credentials credentials) {
        int activado = 0;
        if (credentials.isActivado()) {
            activado = 1;
        }
        try (Connection connection = dbConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Credentials SET password = ?, activado = ?, fechaActivacion = ?, codigoActivacion = ?, rol = ? WHERE email = ?")) {
            preparedStatement.setString(1, credentials.getPassword());
            preparedStatement.setInt(2, activado);
            preparedStatement.setDate(3, java.sql.Date.valueOf(LocalDateTime.now().toLocalDate()));
            preparedStatement.setString(4, credentials.getCodigoActivacion());
            preparedStatement.setString(5, credentials.getRol());
            preparedStatement.setString(6, credentials.getEmail());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    public Credentials getByCodigoActivacion(String id) {
        Credentials credentials = null;
        try (Connection connection = dbConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Credentials WHERE codigoActivacion = ?")) {
            preparedStatement.setString(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {

                credentials = new Credentials(rs.getString("email"),
                        rs.getString("password"),
                        rs.getInt("activado") == 1,
                        rs.getTimestamp("fechaActivacion").toLocalDateTime(),
                        rs.getString("codigoActivacion"),
                        rs.getString("rol"),
                        "",
                        "","");
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return credentials;
    }

public void forgotPassword(Credentials credentials) {
        try (Connection connection = dbConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Credentials SET temporalPassword = ? WHERE email = ?")) {
            preparedStatement.setString(1, credentials.getTemporalPassword());
            preparedStatement.setString(2, credentials.getEmail());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }



    public Credentials getByEmail(String email) {
        Credentials credentials = null;
        try (Connection connection = dbConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Credentials WHERE email = ?")) {
            preparedStatement.setString(1, email);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {

                credentials = new Credentials(rs.getString("email"),
                        rs.getString("password"),
                        rs.getInt("activado") == 1,
                        rs.getTimestamp("fechaActivacion").toLocalDateTime(),
                        rs.getString("codigoActivacion"),
                        rs.getString("rol"),
                        "",
                        rs.getString("refreshToken"),
                        rs.getString("temporalPassword"));
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return credentials;
    }

    public void updateRefreshToken(Credentials credentials) {
        try (Connection connection = dbConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Credentials SET refreshToken = ? WHERE email = ?")) {
            preparedStatement.setString(1, credentials.getRefreshToken());
            preparedStatement.setString(2, credentials.getEmail());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

   public Credentials getByRefreshToken(String refreshToken) {
        Credentials credentials = null;
        try (Connection connection = dbConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Credentials WHERE refreshToken = ?")) {
            preparedStatement.setString(1, refreshToken);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {

                credentials = new Credentials(rs.getString("email"),
                        rs.getString("password"),
                        rs.getInt("activado") == 1,
                        rs.getTimestamp("fechaActivacion").toLocalDateTime(),
                        rs.getString("codigoActivacion"),
                        rs.getString("rol"),
                        rs.getString("accessToken"),
                        rs.getString("refreshToken"),
                        rs.getString("temporalPassword"));
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return credentials;
    }
    public void updateAccessToken(Credentials credentials) {
        try (Connection connection = dbConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Credentials SET accessToken = ? WHERE email = ?")) {
            preparedStatement.setString(1, credentials.getAccessToken());
            preparedStatement.setString(2, credentials.getEmail());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    public Credentials getByAccessToken(String accessToken) {
        Credentials credentials = null;
        try (Connection connection = dbConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Credentials WHERE accessToken = ?")) {
            preparedStatement.setString(1, accessToken.strip());
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {

                credentials = new Credentials(rs.getString("email"),
                        rs.getString("password"),
                        rs.getInt("activado") == 1,
                        rs.getTimestamp("fechaActivacion").toLocalDateTime(),
                        rs.getString("codigoActivacion"),
                        rs.getString("rol"),
                        rs.getString("accessToken"),
                        rs.getString("refreshToken"),
                        rs.getString("temporalPassword"));
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return credentials;
    }

    public void cambiarContrasenya(Credentials credentials) {
        try (Connection connection = dbConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Credentials SET password = ? WHERE email = ?")) {
            preparedStatement.setString(1, credentials.getPassword());
            preparedStatement.setString(2, credentials.getEmail());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }
}
