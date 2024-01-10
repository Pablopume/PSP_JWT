package jakarta.rest;


import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import modelo.Credentials;
import servicios.ServiciosCredentials;

import java.time.Duration;
import java.time.LocalDateTime;

@Path("/activar-cuenta")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ActivarRest {

    private final ServiciosCredentials serviciosCredentials;
    @Inject
    public ActivarRest( ServiciosCredentials serviciosCredentials) {

        this.serviciosCredentials = serviciosCredentials;
    }

    @GET
   // @RolesAllowed({"ADMIN", "USER"})
    public Response activarCuenta(@QueryParam("codigo") String codigoActivacion) {

        Credentials credentials= serviciosCredentials.getByCodigoActivacion(codigoActivacion);
        if (credentials != null) {
            if (Duration.between(credentials.getFechaActivacion(), LocalDateTime.now()).getSeconds()>=300) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Ha pasado el tiempo ").build();
            }
            else {
                credentials.setActivado(true);
                serviciosCredentials.update(credentials);
                return Response.ok("Cuenta activada exitosamente").build();
            }
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Código de activación inválido").build();
        }
    }
}
