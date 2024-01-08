package jakarta.rest;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import modelo.Libro;
import servicios.ServiciosLibro;

import java.util.List;

@Path("/libros")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LibrosRest {
    private final ServiciosLibro serviciosLibro;

    @Inject
    public LibrosRest(ServiciosLibro serviciosLibro) {
        this.serviciosLibro = serviciosLibro;
    }

    @GET
    @RolesAllowed({"ADMIN", "USER"})
    public List<Libro> getAllLibros() {
        return serviciosLibro.getAlll();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({"ADMIN"})
    public Response deleteLibro(@PathParam("id") int id) {
        serviciosLibro.deleteLibro(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @POST
    @RolesAllowed({"ADMIN", "USER"})
    public Libro addLibro(Libro libro) {
        serviciosLibro.addLibro(libro);
        return libro;
    }

    @PUT
    @RolesAllowed({"ADMIN", "USER"})
    public Libro updateLibro(Libro libro) {
        serviciosLibro.updateLibro(libro);
        return libro;
    }
}
