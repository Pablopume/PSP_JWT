package dao.impl;

import dao.DBConnectionPool;
import dao.DaoLibros;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import modelo.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class DaoLibrosImpl implements DaoLibros {
    private final DBConnectionPool dbConnectionPool;
    @Inject
    public DaoLibrosImpl(DBConnectionPool dbConnectionPool) {
        this.dbConnectionPool = dbConnectionPool;
    }
    public List<Order> getAlll(){
        List<Order> orders = new ArrayList<>();
        try (Connection connection = dbConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(Querys.SELECT_FROM_ORDERS)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                orders.add(new Order(resultSet.getInt(DaoConstantes.ID),
                        resultSet.getInt(DaoConstantes.CUSTOMER_ID),
                        resultSet.getDate(DaoConstantes.ORDER_DATE).toLocalDate(),
                        resultSet.getInt(DaoConstantes.TABLE_ID)));
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return orders;
    }
    public void addLibro(Order order) {
        try (Connection connection = dbConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(Querys.INSERT_INTO_ORDERS_ID_CUSTOMER_ID_ORDER_DATE_TABLE_ID_VALUES)) {
            preparedStatement.setInt(1, order.getId());
            preparedStatement.setInt(2, order.getCustomerId());
            preparedStatement.setDate(3, Date.valueOf(order.getOrderDate()) );
            preparedStatement.setInt(4, order.getTableId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }
    public void updateLibro(Order order) {
        try (Connection connection = dbConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(Querys.UPDATE_ORDERS_SET_CUSTOMER_ID_ORDER_DATE_TABLE_ID_WHERE_ID)) {
            preparedStatement.setInt(1, order.getId());
            preparedStatement.setInt(2, order.getCustomerId());
            preparedStatement.setDate(3,Date.valueOf(order.getOrderDate()));
            preparedStatement.setInt(4, order.getTableId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }
    public void deleteLibro(int id) {
        try (Connection connection = dbConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(Querys.DELETE_FROM_ORDERS_WHERE_ID)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }
    public List<Order> getLibrosAutor(int customerId) {
        List<Order> orders = new ArrayList<>();
        try (Connection connection = dbConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(Querys.SELECT_FROM_ORDERS_WHERE_CUSTOMER_ID)) {
            preparedStatement.setInt(1, customerId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                orders.add(new Order(resultSet.getInt(DaoConstantes.ID),
                        resultSet.getInt(DaoConstantes.CUSTOMER_ID),
                        resultSet.getDate(DaoConstantes.ORDER_DATE1).toLocalDate(),
                        resultSet.getInt(DaoConstantes.TABLE_ID1)));
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return orders;
    }
}
