package org.esbtools.gateway.resync.rest;

import org.esbtools.gateway.resync.ResyncRequest;
import org.esbtools.gateway.resync.ResyncResponse;
import org.esbtools.gateway.resync.ResyncService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
public class ResyncGateway {

    private static final Logger LOGGER= LoggerFactory.getLogger(ResyncGateway.class);

    @Autowired
    private ResyncService resyncService = new ResyncService();

    @POST
    @Path("/resync")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response resync(ResyncRequest request) {

        LOGGER.info(request.toString());

        ResyncResponse resyncResponse = resyncService.resync(request);

        try {
            if(ResyncResponse.Status.Error.equals(resyncResponse.getStatus())) {
                return Response.status(Response.Status.BAD_REQUEST).entity(resyncResponse).build();
            } else {
                return Response.status(Response.Status.OK).entity(resyncResponse).build();
            }
        } catch (RuntimeException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(resyncResponse).build();
        }
    }

}
