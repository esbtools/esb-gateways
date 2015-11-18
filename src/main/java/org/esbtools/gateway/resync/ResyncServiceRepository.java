package org.esbtools.gateway.resync;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public class ResyncServiceRepository {

    private Set<ResyncService> resyncServices;

    @Autowired
    public ResyncServiceRepository(Set<ResyncService> resyncServices) {
        this.resyncServices = resyncServices;
    }

    public ResyncService getBySystem(String system) {
        ResyncService requestedService = null;
        for (ResyncService resyncService : resyncServices) {
            if (system.equals(resyncService.getSystemName())) {
                requestedService = resyncService;
                break;
            }
        }
        return requestedService;
    }
}
