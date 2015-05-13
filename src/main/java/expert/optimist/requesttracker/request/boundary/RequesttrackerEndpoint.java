package expert.optimist.requesttracker.request.boundary;

import expert.optimist.requesttracker.request.control.RequesttrackerService;
import expert.optimist.requesttracker.request.entity.Request;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Set;

@Path("requests")
public class RequesttrackerEndpoint {

    @Inject
    private RequesttrackerService service;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Set<Request> findByClassName(@QueryParam("className") String className) {
        if (className == null) {
            return service.getAll();
        }
        return service.findByClassName(className);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Request create(Request request) {
        return service.create(request);
    }

}
