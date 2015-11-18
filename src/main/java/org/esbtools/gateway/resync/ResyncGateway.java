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

import static org.esbtools.gateway.resync.ResyncError.ALL_REQUIRED_VALUES_NOT_PRESENT;
import static org.esbtools.gateway.resync.ResyncError.PROBLEM_ENQUEUING;
import static org.esbtools.gateway.resync.ResyncError.withContext;

@RestController
@RequestMapping("/")
public class ResyncGateway {

    private static final Logger LOGGER= LoggerFactory.getLogger(ResyncGateway.class);

    public ResyncGateway() {

    }

    public ResyncGateway(ResyncService resyncService) {
        this.resyncService = resyncService;
    }

    @Autowired
    private ResyncService resyncService;

    @RequestMapping(value="/resync", method=RequestMethod.POST, produces="application/json")
    public ResponseEntity<ResyncResponse> resync(@RequestBody ResyncRequest request) {
        ResponseEntity<ResyncResponse> responseEntity = null;
        ResyncResponse resyncResponse = resyncService.resync(request);

        if(resyncResponse.wasSuccessful()) {
            responseEntity = new ResponseEntity<ResyncResponse>(resyncResponse, HttpStatus.OK);
        } else {
            if(ALL_REQUIRED_VALUES_NOT_PRESENT.equals(resyncResponse.getErrorMessage())) {
                responseEntity = new ResponseEntity<ResyncResponse>(resyncResponse, HttpStatus.BAD_REQUEST);
            } else if(withContext(PROBLEM_ENQUEUING, request.toString()).equals(resyncResponse.getErrorMessage())) {
                responseEntity = new ResponseEntity<ResyncResponse>(resyncResponse, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return responseEntity;
    }

}
