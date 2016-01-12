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
package org.esbtools.gateway.resubmit.service;

import org.apache.commons.lang3.StringUtils;
import org.esbtools.gateway.exception.InvalidSystemException;
import org.esbtools.gateway.exception.SystemConfigurationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public class ResubmitServiceRepository {

    private Set<ResubmitService> resubmitServices;

    @Autowired
    public ResubmitServiceRepository(Set<ResubmitService> resubmitServices) {
        this.resubmitServices = resubmitServices;
    }

    public ResubmitService getBySystem(String system) {
        if(StringUtils.isBlank(system)) {
            throw new InvalidSystemException(system);
        } else {
            for (ResubmitService resubmitService : resubmitServices) {
                if (system.equalsIgnoreCase(resubmitService.getSystemName())) {
                    return resubmitService;
                }
            }
        }
        throw new SystemConfigurationException(system);
    }
}
