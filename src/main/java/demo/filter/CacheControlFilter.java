package demo.filter;

import java.io.IOException;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.ext.Provider;

@Provider
public final class CacheControlFilter implements ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext req, ContainerResponseContext res) throws IOException {
        if (HttpMethod.GET.equals(req.getMethod())) {
            CacheControl cc = new CacheControl();
            cc.setMaxAge(4);
            req.getHeaders().add("Cache-Control", cc.toString());
        }
    }

}