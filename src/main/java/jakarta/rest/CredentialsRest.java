package jakarta.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import modelo.Credentials;
import servicios.ServiciosCredentials;

@Path("/credentials")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CredentialsRest {
    private final ServiciosCredentials serviciosCredentials;
    @Inject
    public CredentialsRest(ServiciosCredentials serviciosCredentials) {
        this.serviciosCredentials = serviciosCredentials;
    }

    @POST
    public Credentials addCredentials(Credentials credentials) {
        return serviciosCredentials.addCredentials(credentials);
    }

    @GET
    @Path("/forgot-password")
    public Response forgotPassword(@QueryParam("email")String email) {
        serviciosCredentials.forgotPassword(email);
        return Response.ok("Se ha enviado un correo con la nueva contraseña").build();
    }
    @GET
    @Path("/login")
    public Response getLogin(@QueryParam("user") String user, @QueryParam("password") String password) {
        Response response;
        boolean isAuthenticated = serviciosCredentials.doLogin(user, password);
        if (isAuthenticated) {
            response = Response.status(Response.Status.NO_CONTENT).build();
        } else {
            response = Response.status(Response.Status.UNAUTHORIZED).build();
        }
        return response;
    }

    @GET
    @Path("/refreshToken")
    public Response refreshToken(@QueryParam("refreshToken") String refreshToken) {
        Response response;
        String newToken = serviciosCredentials.refreshToken(refreshToken);
        if (newToken != null) {
            response = Response.ok(newToken).build();
        } else {
            response = Response.status(Response.Status.UNAUTHORIZED).build();
        }
        return response;
    }

}
