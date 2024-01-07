package jakarta.rest;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import modelo.Autor;
import servicios.ServicesAutor;


@Path("/autores")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AutorRest {
    private final ServicesAutor servicesAutorImpl;

    @Inject
    public AutorRest(ServicesAutor servicesAutorImpl) {
        this.servicesAutorImpl = servicesAutorImpl;
    }

    @POST
    @RolesAllowed({"ADMIN", "USER"})
    public Autor addAutor(Autor autor) {
        return servicesAutorImpl.add(autor);
    }


}
