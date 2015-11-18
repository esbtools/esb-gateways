package org.esbtools.gateway.resync;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/")
public class ResyncGateway {

    private static final Logger LOGGER= LoggerFactory.getLogger(ResyncGateway.class);

    @Autowired
    private ResyncService resyncService;

    @RequestMapping(value="/resync", method=RequestMethod.POST, produces="application/json")
    public ResponseEntity<ResyncResponse> resync(@RequestBody ResyncRequest request) {
        ResponseEntity<ResyncResponse> responseEntity = null;
        ResyncResponse resyncResponse = null;
        try {
            resyncResponse = resyncService.resync(request);
            if(ResyncResponse.Status.Error.equals(resyncResponse.getStatus())) {
                responseEntity = new ResponseEntity<ResyncResponse>(resyncResponse, HttpStatus.BAD_REQUEST);
            } else {
                responseEntity = new ResponseEntity<ResyncResponse>(resyncResponse, HttpStatus.OK);
            }
        } catch (RuntimeException e) {
            responseEntity = new ResponseEntity<ResyncResponse>(resyncResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

}
