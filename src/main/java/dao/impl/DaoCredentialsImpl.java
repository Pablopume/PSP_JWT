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
             PreparedStatement preparedStatement = connection.prepareStatement(DaoConstantes.INSERT_INTO_CREDENTIALS_EMAIL_PASSWORD_ACTIVADO_FECHA_ACTIVACION_CODIGO_ACTIVACION_ROL_VALUES);
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
             PreparedStatement preparedStatement = connection.prepareStatement(Querys.UPDATE_CREDENTIALS_SET_PASSWORD_ACTIVADO_FECHA_ACTIVACION_CODIGO_ACTIVACION_ROL_WHERE_EMAIL)) {
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
             PreparedStatement preparedStatement = connection.prepareStatement(Querys.SELECT_FROM_CREDENTIALS_WHERE_CODIGO_ACTIVACION)) {
            preparedStatement.setString(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {

                credentials = new Credentials(rs.getString(DaoConstantes.EMAIL),
                        rs.getString(DaoConstantes.PASSWORD),
                        rs.getInt(DaoConstantes.ACTIVADO) == 1,
                        rs.getTimestamp(DaoConstantes.FECHA_ACTIVACION).toLocalDateTime(),
                        rs.getString(DaoConstantes.CODIGO_ACTIVACION),
                        rs.getString(DaoConstantes.ROL),
                        DaoConstantes.EMPTY,
                        DaoConstantes.EMPTY, DaoConstantes.EMPTY);
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return credentials;
    }

    public void cambiarCodigoActivacion(Credentials credentials) {
        try (Connection connection = dbConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(Querys.UPDATE_CREDENTIALS_SET_CODIGO_ACTIVACION_WHERE_EMAIL)) {
            preparedStatement.setString(1, credentials.getCodigoActivacion());
            preparedStatement.setString(2, credentials.getEmail());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

public void forgotPassword(Credentials credentials) {
        try (Connection connection = dbConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(Querys.UPDATE_CREDENTIALS_SET_TEMPORAL_PASSWORD_WHERE_EMAIL)) {
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
             PreparedStatement preparedStatement = connection.prepareStatement(Querys.SELECT_FROM_CREDENTIALS_WHERE_EMAIL)) {
            preparedStatement.setString(1, email);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {

                credentials = new Credentials(rs.getString(DaoConstantes.EMAIL),
                        rs.getString(DaoConstantes.PASSWORD),
                        rs.getInt(DaoConstantes.ACTIVADO) == 1,
                        rs.getTimestamp(DaoConstantes.FECHA_ACTIVACION).toLocalDateTime(),
                        rs.getString(DaoConstantes.CODIGO_ACTIVACION),
                        rs.getString(DaoConstantes.ROL),
                        DaoConstantes.EMPTY,
                        rs.getString(DaoConstantes.REFRESH_TOKEN),
                        rs.getString(DaoConstantes.TEMPORAL_PASSWORD));
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return credentials;
    }

    public void updateRefreshToken(Credentials credentials) {
        try (Connection connection = dbConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(Querys.UPDATE_CREDENTIALS_SET_REFRESH_TOKEN_WHERE_EMAIL)) {
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
             PreparedStatement preparedStatement = connection.prepareStatement(Querys.SELECT_FROM_CREDENTIALS_WHERE_REFRESH_TOKEN)) {
            preparedStatement.setString(1, refreshToken);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {

                credentials = new Credentials(rs.getString(DaoConstantes.EMAIL),
                        rs.getString(DaoConstantes.PASSWORD),
                        rs.getInt(DaoConstantes.ACTIVADO) == 1,
                        rs.getTimestamp(DaoConstantes.FECHA_ACTIVACION).toLocalDateTime(),
                        rs.getString(DaoConstantes.CODIGO_ACTIVACION),
                        rs.getString(DaoConstantes.ROL),
                        rs.getString(DaoConstantes.ACCESS_TOKEN),
                        rs.getString(DaoConstantes.REFRESH_TOKEN),
                        rs.getString(DaoConstantes.TEMPORAL_PASSWORD));
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return credentials;
    }
    public void updateAccessToken(Credentials credentials) {
        try (Connection connection = dbConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(Querys.UPDATE_CREDENTIALS_SET_ACCESS_TOKEN_WHERE_EMAIL)) {
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
             PreparedStatement preparedStatement = connection.prepareStatement(Querys.SELECT_FROM_CREDENTIALS_WHERE_ACCESS_TOKEN)) {
            preparedStatement.setString(1, accessToken.strip());
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {

                credentials = new Credentials(rs.getString(DaoConstantes.EMAIL),
                        rs.getString(DaoConstantes.PASSWORD),
                        rs.getInt(DaoConstantes.ACTIVADO) == 1,
                        rs.getTimestamp(DaoConstantes.FECHA_ACTIVACION).toLocalDateTime(),
                        rs.getString(DaoConstantes.CODIGO_ACTIVACION),
                        rs.getString(DaoConstantes.ROL),
                        rs.getString(DaoConstantes.ACCESS_TOKEN),
                        rs.getString(DaoConstantes.REFRESH_TOKEN),
                        rs.getString(DaoConstantes.TEMPORAL_PASSWORD));
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return credentials;
    }

    public void cambiarContrasenya(Credentials credentials) {
        try (Connection connection = dbConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(Querys.UPDATE_CREDENTIALS_SET_PASSWORD_WHERE_EMAIL)) {
            preparedStatement.setString(1, credentials.getPassword());
            preparedStatement.setString(2, null);
            preparedStatement.setString(3, credentials.getEmail());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }
}
