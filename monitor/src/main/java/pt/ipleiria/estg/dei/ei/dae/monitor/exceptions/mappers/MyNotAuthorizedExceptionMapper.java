package pt.ipleiria.estg.dei.ei.dae.monitor.exceptions.mappers;

import jakarta.ws.rs.NotAuthorizedException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.logging.Logger;

@Provider
public class MyNotAuthorizedExceptionMapper implements ExceptionMapper<NotAuthorizedException> {
    private static final Logger logger =
            Logger.getLogger(NotAuthorizedException.class.getCanonicalName());
    @Override
    public Response toResponse(NotAuthorizedException exception) {
        String errorMsg = exception.getMessage();
        logger.warning("ERROR: " + errorMsg);
        return Response
                .status(Response.Status.UNAUTHORIZED)
                .entity(errorMsg)
                .build();
    }
}
