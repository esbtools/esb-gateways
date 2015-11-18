package org.esbtools.gateway.resync;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;
import java.util.Set;

@Component
public class ResyncConfigurations {

    private final Set<ResyncConfiguration> resyncConfigurations;

    @Autowired
    public ResyncConfigurations(Set<ResyncConfiguration> configurations) {
        this.resyncConfigurations = configurations;
    }

    public ResyncConfiguration getBySystem(String system) {
        for (ResyncConfiguration configuration : resyncConfigurations) {
            if (system.equals(configuration.getEndSystem())) {
                return configuration;
            }
        }
        throw new NoSuchElementException(String.format("No resync configuration configured for %s system out of known configurations %s", system, resyncConfigurations));
    }
}
