package org.esbtools.gateway.resync.exception;

import org.esbtools.gateway.resync.service.ResyncErrorMessages;

public class SystemConfigurationException extends RuntimeException {
    public SystemConfigurationException(String unconfiguredSystem){
        super(ResyncErrorMessages.systemNotConfigured(unconfiguredSystem));
    }
}
