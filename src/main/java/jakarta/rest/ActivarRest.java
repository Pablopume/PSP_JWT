package jakarta.rest;


import jakarta.RestConstantes;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import modelo.Credentials;
import servicios.ServiciosCredentials;

@Path(RestConstantes.ACTIVAR_CUENTA)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ActivarRest {

    private final ServiciosCredentials serviciosCredentials;

    @Inject
    public ActivarRest(ServiciosCredentials serviciosCredentials) {

        this.serviciosCredentials = serviciosCredentials;
    }

    @GET
    public Response activarCuenta(@QueryParam(RestConstantes.CODIGO) String codigoActivacion) {


        Credentials credentials = serviciosCredentials.getByCodigoActivacion(codigoActivacion);
        if (credentials != null) {
            return Response.ok(RestConstantes.CUENTA_ACTIVADA_CON_EXITO).build();
        }else {
            return Response.status(Response.Status.NOT_FOUND).build();
    }
}
}
