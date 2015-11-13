package org.esbtools.gateway.resync;


import java.util.List;

import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * Created by dhaynes on 11/12/15.
 */
public class ResyncRequest {

    private String entity;
    private String system;
    private String key;
    private List<String> values;

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

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

    @Override
    public String toString() {
        return String.format("ResyncRequest [entity=%s, system=%s, key=%s, values=%s]", entity, system, key, values);
    }
}
