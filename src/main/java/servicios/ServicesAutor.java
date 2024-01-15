package servicios;

import modelo.Customer;

import java.util.List;

public interface ServicesAutor {
    Customer add(Customer customer);

    Customer update(Customer customer);


    List<Customer> getAll();

}
