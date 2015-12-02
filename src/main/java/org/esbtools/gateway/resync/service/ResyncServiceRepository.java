/*
 Copyright 2015 esbtools Contributors and/or its affiliates.

 This file is part of esbtools.

 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
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
                if (system.equalsIgnoreCase(resyncService.getSystemName())) {
                    return resyncService;
                }
            }
        }
        throw new SystemConfigurationException(system);
    }
}
