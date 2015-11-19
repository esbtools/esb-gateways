package org.esbtools.gateway.resync.exception;

import org.esbtools.gateway.resync.ResyncRequest;
import org.esbtools.gateway.resync.service.ResyncErrorMessages;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason=ResyncErrorMessages.INCOMPLETE_REQUEST)
public class IncompleteRequestException extends RuntimeException {

    public IncompleteRequestException(ResyncRequest resyncRequest){
        super(ResyncErrorMessages.incompleteRequest(resyncRequest));
    }

}
