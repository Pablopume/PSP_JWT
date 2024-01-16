package jakarta.errores;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import modelo.exceptions.Exception401;


@Provider
public class Exception401Mapper implements ExceptionMapper<Exception401> {

    public Response toResponse(Exception401 exception) {
        ErrorApp apiError = new ErrorApp(exception.getMessage());
        return Response.status(Response.Status.NOT_FOUND).entity(apiError)
                .type(MediaType.APPLICATION_JSON_TYPE).build();
    }
}
