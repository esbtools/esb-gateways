package org.esbtools.gateway.resync.exception;

import org.esbtools.gateway.resync.ResyncRequest;
import org.esbtools.gateway.resync.service.ResyncErrorMessages;

public class ResyncFailedException extends RuntimeException {

    public ResyncFailedException(ResyncRequest failedResyncRequest){
        super(ResyncErrorMessages.resyncFailed(failedResyncRequest));
    }

}
