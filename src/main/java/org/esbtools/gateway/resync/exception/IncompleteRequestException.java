package org.esbtools.gateway.resync.exception;

import org.esbtools.gateway.resync.ResyncRequest;
import org.esbtools.gateway.resync.service.ResyncErrorMessages;

public class IncompleteRequestException extends RuntimeException {

    public IncompleteRequestException(ResyncRequest resyncRequest){
        super(ResyncErrorMessages.incompleteRequest(resyncRequest));
    }

}
