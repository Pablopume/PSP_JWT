package jakarta.rest;


import jakarta.RestConstantes;
import jakarta.annotation.security.DeclareRoles;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@ApplicationPath(RestConstantes.API)
@DeclareRoles({RestConstantes.USER, RestConstantes.ADMIN})
public class JAXRSApplication extends Application {

}
