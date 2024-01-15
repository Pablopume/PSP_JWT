package jakarta.rest;

import jakarta.RestConstantes;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import modelo.Customer;
import servicios.ServicesAutor;

import java.util.List;


@Path(RestConstantes.CUSTOMER)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AutorRest {
    private final ServicesAutor servicesAutorImpl;

    @Inject
    public AutorRest(ServicesAutor servicesAutorImpl) {
        this.servicesAutorImpl = servicesAutorImpl;
    }

    @POST
    public Customer addAutor(Customer customer) {
        return servicesAutorImpl.add(customer);
    }

    @RolesAllowed({RestConstantes.ADMIN, RestConstantes.USER})
    @GET
    public List<Customer> getAllAutores() {
        return servicesAutorImpl.getAll();
    }
}
