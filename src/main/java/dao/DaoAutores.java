package dao;

import modelo.Customer;

import java.util.List;

public interface DaoAutores {
    void add(Customer customer);

    void update(Customer customer);

    List<Customer> getAll();


}
