package org.esbtools.gateway.resync.exception;

import org.esbtools.gateway.resync.service.ResyncErrorMessages;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason=ResyncErrorMessages.SYSTEM_NOT_CONFIGURED)
public class SystemConfigurationException extends RuntimeException {
    public SystemConfigurationException(String unconfiguredSystem){
        super(ResyncErrorMessages.systemNotConfigured(unconfiguredSystem));
    }
}
