package jakarta.rest;

import jakarta.RestConstantes;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import modelo.Order;
import servicios.ServiciosLibro;

import java.util.List;

@Path(RestConstantes.ORDER)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LibrosRest {
    private final ServiciosLibro serviciosLibro;

    @Inject
    public LibrosRest(ServiciosLibro serviciosLibro) {
        this.serviciosLibro = serviciosLibro;
    }

    @GET
    @RolesAllowed({RestConstantes.ADMIN, RestConstantes.USER})
    public List<Order> getAllLibros() {
        return serviciosLibro.getAlll();
    }

    @DELETE
    @Path(RestConstantes.ID)
    @RolesAllowed({RestConstantes.ADMIN})
    public Response deleteLibro(@PathParam(RestConstantes.ID1) int id) {
        serviciosLibro.deleteLibro(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @POST
    @RolesAllowed({RestConstantes.ADMIN, RestConstantes.USER})
    public Order addLibro(Order order) {
        serviciosLibro.addLibro(order);
        return order;
    }

    @PUT
    @RolesAllowed({RestConstantes.ADMIN, RestConstantes.USER})
    public Order updateLibro(Order order) {
        serviciosLibro.updateLibro(order);
        return order;
    }
}
