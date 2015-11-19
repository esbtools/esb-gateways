package org.esbtools.gateway.resync.exception;

import org.esbtools.gateway.resync.service.ResyncErrorMessages;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason=ResyncErrorMessages.INCOMPLETE_REQUEST)
public class InvalidSystemException extends RuntimeException {

    public InvalidSystemException(String system){
        super(ResyncErrorMessages.invalidSystem(system));
    }

}
