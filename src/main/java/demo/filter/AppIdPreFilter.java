package demo.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

import com.amazonaws.util.CollectionUtils;

@PreMatching
@Provider
public final class AppIdPreFilter implements ContainerRequestFilter {

    private static final Collection<String> APPID_TYPES = Arrays.asList("abc", "xyz");

    @Override
    public void filter(ContainerRequestContext ctx) throws IOException {
        List<String> appid = ctx.getUriInfo().getQueryParameters().get("appid");
        if (CollectionUtils.isNullOrEmpty(appid)) {
            ctx.abortWith(Response.status(Status.BAD_REQUEST).entity("No appid provided").build());
        } else if (appid.size() != 1) {
            ctx.abortWith(Response.status(Status.BAD_REQUEST).entity("Too much appid: " + appid).build());
        } else if (!APPID_TYPES.contains(appid.get(0))) {
            ctx.abortWith(Response.status(Status.BAD_REQUEST).entity("Unsupported appid: " + appid).build());
        }
    }

}