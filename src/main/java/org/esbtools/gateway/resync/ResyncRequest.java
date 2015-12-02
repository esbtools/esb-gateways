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
package org.esbtools.gateway.resync;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.esbtools.gateway.resync.exception.IncompleteRequestException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.StringWriter;
import java.util.List;

import static org.apache.commons.collections4.CollectionUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.isBlank;

@XmlRootElement(name="SyncRequest")
public class ResyncRequest {

    private String entity;
    private String system;
    private String key;
    private List<String> values;

    @XmlElement(name="EntityName")
    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    @XmlElement(name="System")
    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    @XmlElement(name="KeyName")
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @XmlElement( name="KeyValue" )
    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

    public void ensureRequiredPropertiesHaveValues() {
        if(isBlank(entity) || isBlank(system) || isBlank(key) || isEmpty(values)) {
            throw new IncompleteRequestException(this);
        }
    }

    public String toXML() {
        StringWriter thisXML = new StringWriter();
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(ResyncRequest.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            jaxbMarshaller.marshal(this, thisXML);

        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
        return thisXML.toString();
    }

    public String toJson() {
        String thisJson;
        try {
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            thisJson = ow.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return thisJson;
    }

    @Override
    public String toString() {
        return String.format("ResyncRequest [entity=%s, system=%s, key=%s, values=%s]", entity, system, key, values);
    }
}
