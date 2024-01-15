package servicios.impl;

import dao.DaoAutores;
import dao.HasheoContrasenyas;
import jakarta.inject.Inject;
import modelo.Customer;
import servicios.ServicesAutor;

import java.util.List;

public class ServicesAutorImpl implements ServicesAutor {
    private final DaoAutores daoAutores;

    @Inject
    public ServicesAutorImpl(DaoAutores daoAutores, HasheoContrasenyas hasheoContrasenyas) {
        this.daoAutores = daoAutores;

    }
    public Customer add(Customer customer) {

        daoAutores.add(customer);
        return customer;
    }
    public Customer update(Customer customer) {
        daoAutores.update(customer);
        return customer;
    }


    @Override
    public List<Customer> getAll() {
        return daoAutores.getAll();
    }


}
