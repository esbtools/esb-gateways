package org.esbtools.gateway.resync.exception;

import org.esbtools.gateway.resync.service.ResyncErrorMessages;

public class InvalidSystemException extends RuntimeException {

    public InvalidSystemException(String system){
        super(ResyncErrorMessages.invalidSystem(system));
    }

}
