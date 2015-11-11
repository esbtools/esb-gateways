package org.esbtools.gateway.resync;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

public class GatewayApplication extends Application {
    private Set<Object> singletons = new HashSet<Object>();

    public GatewayApplication() {
        singletons.add(new GatewayResource());
    }

    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }
}
