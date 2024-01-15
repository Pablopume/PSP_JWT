package dao.impl;

import dao.DBConnectionPool;
import dao.DaoAutores;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import modelo.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class DaoAutoresImpl implements DaoAutores {
    private final DBConnectionPool dbConnectionPool;

    @Inject
    public DaoAutoresImpl(DBConnectionPool dbConnectionPool) {
        this.dbConnectionPool = dbConnectionPool;
    }

    public void add(Customer customer) {
        try (Connection connection = dbConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(Querys.INSERT_INTO_CUSTOMER_VALUES)) {
            preparedStatement.setInt(1, customer.getId());
            preparedStatement.setString(2, customer.getName());
            preparedStatement.setString(3, customer.getLastName());
            preparedStatement.setDate(4, Date.valueOf(customer.getBirthDate()));
            preparedStatement.setString(5, customer.getPhone());
            preparedStatement.setString(6, customer.getEmail());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    public void update(Customer customer) {


        try (Connection connection = dbConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(Querys.UPDATE_CUSTOMER_SET_NAME_LAST_NAME_DOB_PHONE_MAIL_WHERE_ID)) {
            preparedStatement.setString(1, customer.getName());
            preparedStatement.setString(2, customer.getLastName());
            preparedStatement.setDate(3, Date.valueOf(customer.getBirthDate()));
            preparedStatement.setString(4, customer.getPhone());
            preparedStatement.setString(5, customer.getEmail());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }




    public List<Customer> getAll(){
        List<Customer> autores = new ArrayList<>();
        try (Connection connection = dbConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(Querys.SELECT_FROM_CUSTOMER)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                autores.add(new Customer(
            rs.getInt(DaoConstantes.ID),
                        rs.getString(DaoConstantes.NAME),
                        rs.getString(DaoConstantes.LAST_NAME),
                        rs.getString(DaoConstantes.MAIL),
                        rs.getString(DaoConstantes.PHONE),
                        rs.getDate(DaoConstantes.DOB).toLocalDate()
                ));
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return autores;
    }

}
