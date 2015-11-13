package org.esbtools.gateway.resync.rest;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

public class ResyncGatewayApplication extends Application {
    private Set<Object> singletons = new HashSet<Object>();

    public ResyncGatewayApplication() {
        singletons.add(new ResyncGateway());
    }

    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }
}
