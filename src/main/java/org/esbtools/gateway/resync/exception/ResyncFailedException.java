package org.esbtools.gateway.resync.exception;

import org.esbtools.gateway.resync.ResyncRequest;
import org.esbtools.gateway.resync.service.ResyncErrorMessages;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.INTERNAL_SERVER_ERROR, reason= ResyncErrorMessages.RESYNC_FAILED)
public class ResyncFailedException extends RuntimeException {

    public ResyncFailedException(ResyncRequest failedResyncRequest){
        super(ResyncErrorMessages.resyncFailed(failedResyncRequest));
    }

}
