package org.esbtools.gateway.resync.config;

import java.util.List;

/**
 * Created by dhaynes on 11/12/15.
 */
public class ResyncGatewayConfiguration {

    private List<ResyncConfiguration> resyncConfigurations;

    public List<ResyncConfiguration> getResyncConfigurations() {
        return resyncConfigurations;
    }

    public void setResyncConfigurations(List<ResyncConfiguration> resyncConfigurations) {
        this.resyncConfigurations = resyncConfigurations;
    }
}
