package jakarta.rest;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import modelo.Autor;
import servicios.ServicesAutor;

import java.time.Duration;
import java.time.LocalDateTime;

@Path("/activar-cuenta")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ActivarRest {
    private final ServicesAutor autorService;

    @Inject
    public ActivarRest(ServicesAutor autorService) {
        this.autorService = autorService;
    }

    @GET
    @RolesAllowed({"ADMIN", "USER"})
    public Response activarCuenta(@QueryParam("codigo") String codigoActivacion) {
        Autor autor = autorService.getByCodigoActivacion(codigoActivacion);
        if (autor != null) {
            if (Duration.between(autor.getFechaActivacion(), LocalDateTime.now()).getSeconds()>=300) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Ha pasado el tiempo ").build();
            }
            else {
                autor.setActivado(true);
                autorService.update(autor);
                return Response.ok("Cuenta activada exitosamente").build();
            }
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Código de activación inválido").build();
        }
    }
}
