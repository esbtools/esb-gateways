package org.esbtools.gateway.resync;


import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.StringWriter;
import java.util.List;

import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * Created by dhaynes on 11/12/15.
 */
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

    public boolean hasValuesForRequiredProperties() {
        if(isNotBlank(entity) && isNotBlank(system) && isNotBlank(key) && isNotEmpty(values)) {
            return true;
        } else {
            return false;
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

    @Override
    public String toString() {
        return String.format("ResyncRequest [entity=%s, system=%s, key=%s, values=%s]", entity, system, key, values);
    }
}
