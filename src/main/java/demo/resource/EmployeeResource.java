package demo.resource;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import demo.model.Employee;
import demo.repository.EmployeeRepository;

@Path("/employee")
public final class EmployeeResource {

    private static final Logger LOGGER = LogManager.getLogger(EmployeeResource.class);

    private EmployeeRepository repo;

    public EmployeeResource() {
        this.repo = new EmployeeRepository();
    }

    public EmployeeResource(EmployeeRepository repo) {
        this.repo = repo;
    }

    @GET
    @Path("/{uuid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmployee(@PathParam("uuid") String uuid, @QueryParam("appid") String appId) {
        try {
            Employee employee = repo.getEmployee(appId, uuid);
            return Response.ok(employee).build();
        } catch (Exception e) {
            LOGGER.error(() -> e.getMessage(), e);
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{uuid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeEmployee(@PathParam("uuid") String uuid, @QueryParam("appid") String appId) {
        try {
            repo.removeEmployee(appId, uuid);
            return Response.ok().build();
        } catch (IllegalArgumentException e) {
            LOGGER.error(() -> "Can not parse request body, " + e.getMessage(), e);
            return Response.status(Status.BAD_REQUEST).entity("Can not parse request body, " + e.getMessage()).build();
        } catch (Exception e) {
            LOGGER.error(() -> e.getMessage(), e);
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{uuid}")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateEmployee(@PathParam("uuid") String uuid, @QueryParam("appid") String appId,
            String input) {
        try {
            repo.updateEmployee(appId, uuid, input);
            Employee employee = repo.getEmployee(appId, uuid);
            return Response.ok(employee).build();
        } catch (IllegalArgumentException | IOException e) {
            LOGGER.error(() -> "Can not parse request body, " + e.getMessage(), e);
            return Response.status(Status.BAD_REQUEST).entity("Can not parse request body, " + e.getMessage()).build();
        } catch (Exception e) {
            LOGGER.error(() -> e.getMessage(), e);
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

}
