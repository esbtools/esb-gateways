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
package org.esbtools.gateway.resubmit;


import org.esbtools.gateway.GatewayRequest;
import org.esbtools.gateway.exception.IncompleteRequestException;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Map;

import static org.apache.commons.lang3.StringUtils.isBlank;

@XmlRootElement(name="ResubmitRequest")
public class ResubmitRequest extends GatewayRequest {

    private String system;
    private String payload;
    private Map<String, String> headers;

    @XmlElement(name="System")
    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    @XmlElement(name="Payload")
    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    @XmlElement( name="Header" )
    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    @Override
    public void ensureRequiredPropertiesHaveValues() {
        if(isBlank(system) || isBlank(payload)) {
            throw new IncompleteRequestException(this);
        }
    }

    @Override
    public String toString() {
        return String.format("ResubmitRequest [system=%s, payload=%s, headers=%s]", system, payload, headers);
    }
}
