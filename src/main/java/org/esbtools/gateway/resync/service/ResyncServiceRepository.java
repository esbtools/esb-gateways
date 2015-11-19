package org.esbtools.gateway.resync.service;

import org.apache.commons.lang3.StringUtils;
import org.esbtools.gateway.resync.exception.InvalidSystemException;
import org.esbtools.gateway.resync.exception.SystemConfigurationException;
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
        if(StringUtils.isBlank(system)) {
            throw new InvalidSystemException(system);
        } else {
            for (ResyncService resyncService : resyncServices) {
                if (system.equals(resyncService.getSystemName())) {
                    return resyncService;
                }
            }
        }
        throw new SystemConfigurationException(system);
    }
}
