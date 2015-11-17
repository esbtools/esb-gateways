package org.esbtools.gateway.resync;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/")
public class ResyncGateway {

    private static final Logger LOGGER= LoggerFactory.getLogger(ResyncGateway.class);

    @Autowired
    private ResyncService resyncService;

    @RequestMapping(value="/resync", method=RequestMethod.POST, produces="application/json")
    public @ResponseBody ResyncResponse resync(@RequestBody ResyncRequest request) {
        ResyncResponse resyncResponse = null;
        try {
            resyncResponse = resyncService.resync(request);
            if(ResyncResponse.Status.Error.equals(resyncResponse.getStatus())) {
                handleBadRequest();
            } else {
                //do nothing
            }
        } catch (RuntimeException e) {
            handleServerError();
        }
        return resyncResponse;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private void handleBadRequest() {
        //Intentionally does nothing
        //TODO figure out why the status is not coming back correctly
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    private void handleServerError() {
        //Intentionally does nothing
        //TODO figure out why the status is not coming back correctly
    }

}
