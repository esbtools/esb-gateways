package org.esbtools.gateway.resync;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by dhaynes on 11/13/15.
 */

@Service
public class ResyncServiceActiveMQ implements ResyncService {
    private static final Logger LOGGER= LoggerFactory.getLogger(ResyncResponse.class);

    @Override
    public ResyncResponse resync(ResyncRequest resyncRequest) throws RuntimeException {
        LOGGER.debug(resyncRequest.toString());

        ResyncResponse resyncResponse = new ResyncResponse();

        if(resyncRequest.hasValuesForRequiredProperties()) {
            ///Do actual stuff here
            resyncResponse.setStatus(ResyncResponse.Status.Success);
        } else {
            resyncResponse.setErrorMessage("One or more required values was not present");
            resyncResponse.setStatus(ResyncResponse.Status.Error);
        }

        LOGGER.info(resyncResponse.toString());
        return resyncResponse;
    }

}
