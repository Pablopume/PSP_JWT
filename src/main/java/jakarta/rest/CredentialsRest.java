package jakarta.rest;

import jakarta.RestConstantes;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import modelo.Credentials;
import servicios.ServiciosCredentials;

@Path(RestConstantes.CREDENTIALS)
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
    @Path(RestConstantes.FORGOT_PASSWORD)
    public Response forgotPassword(@QueryParam(RestConstantes.EMAIL)String email) {
        serviciosCredentials.forgotPassword(email);
        return Response.ok(RestConstantes.SE_HA_ENVIADO_UN_CORREO_CON_LA_NUEVA_CONTRASENYA).build();
    }
    @GET
    @Path(RestConstantes.LOGIN)
    public Response getLogin(@QueryParam(RestConstantes.USERMINUSC) String user, @QueryParam(RestConstantes.PASSWORD) String password) {
        Response response;
        Credentials result = serviciosCredentials.doLogin(user, password);
        if (result!= null) {
            response = Response.status(Response.Status.NO_CONTENT).header(RestConstantes.AUTHORIZATION, RestConstantes.BEARER + result.getAccessToken()).header(RestConstantes.REFRESH_TOKEN2,result.getRefreshToken()).build();
        } else {
            response = Response.status(Response.Status.UNAUTHORIZED).build();
        }
        return response;
    }

    @GET
    @Path(RestConstantes.REFRESH_TOKEN)
    public Response refreshToken(@QueryParam(RestConstantes.REFRESH_TOKEN1) String refreshToken) {
        Response response;
        String newToken = serviciosCredentials.refreshToken(refreshToken);
        if (newToken != null) {
            response=Response.status(Response.Status.NO_CONTENT).header(RestConstantes.AUTHORIZATION, RestConstantes.BEARER+ newToken).header(RestConstantes.REFRESH_TOKEN2,refreshToken).build();

        } else {
            response = Response.status(Response.Status.UNAUTHORIZED).build();
        }
        return response;
    }

    @PUT
    @Path(RestConstantes.CAMBIAR_CONTRASENYA)
    public Response cambiarContrasenya(Credentials credentials) {
        serviciosCredentials.cambiarContrasenya(credentials);
        return Response.ok(RestConstantes.CONTRASENYA_CAMBIADA_CORRECTAMENTE).build();
    }

}
