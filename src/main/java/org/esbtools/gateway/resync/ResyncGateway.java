package org.esbtools.gateway.resync;

import org.apache.commons.lang3.StringUtils;
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
import static org.esbtools.gateway.resync.ResyncError.SYSTEM_NOT_CONFIGURED;
import static org.esbtools.gateway.resync.ResyncError.withContext;

@RestController
@RequestMapping("/")
public class ResyncGateway {

    private static final Logger LOGGER= LoggerFactory.getLogger(ResyncGateway.class);

    protected ResyncServiceRepository resyncServiceRepository;

    @Autowired
    public ResyncGateway(ResyncServiceRepository resyncServiceRepository) {
        this.resyncServiceRepository = resyncServiceRepository;
    }

    @RequestMapping(value="/resync", method=RequestMethod.POST, produces="application/json")
    public ResponseEntity<ResyncResponse> resync(@RequestBody ResyncRequest resyncRequest) {
        ResponseEntity<ResyncResponse> responseEntity = null;
        ResyncResponse resyncResponse = null;

        if(canResyncSystem(resyncRequest)) {
            resyncResponse = resyncServiceRepository.getBySystem(resyncRequest.getSystem()).resync(resyncRequest);
            responseEntity = handleCompletedResponse(resyncRequest, resyncResponse);
        } else {
            responseEntity = handleIncompleteRequest(resyncRequest);
        }

        return responseEntity;
    }

    private boolean canResyncSystem(ResyncRequest request) {
        boolean canResync = true;
        if(StringUtils.isBlank(request.getSystem())) {
            canResync = false;
        } else if(null == resyncServiceRepository.getBySystem(request.getSystem())) {
            canResync = false;
        }
        return canResync;
    }

    private ResponseEntity<ResyncResponse> handleCompletedResponse(ResyncRequest resyncRequest, ResyncResponse resyncResponse) {
        ResponseEntity<ResyncResponse> responseEntity = getOkResponse(resyncResponse);

        if(resyncResponse.wasSuccessful()) {
            responseEntity = getOkResponse(resyncResponse);
        } else if (ALL_REQUIRED_VALUES_NOT_PRESENT.equals(resyncResponse.getErrorMessage())) {
            responseEntity = getBadRequestResponse(resyncResponse);
        } else if (withContext(PROBLEM_ENQUEUING, resyncRequest.toString()).equals(resyncResponse.getErrorMessage())) {
            responseEntity = getInternalServerErrorResponse(resyncResponse);
        }
        return responseEntity;
    }

    private ResponseEntity handleIncompleteRequest(ResyncRequest resyncRequest) {
        return getBadRequestResponse(new ResyncResponse(ResyncResponse.Status.Error, withContext(SYSTEM_NOT_CONFIGURED, resyncRequest.getSystem())));
    }

    private ResponseEntity getBadRequestResponse(ResyncResponse resyncResponse) {
        return new ResponseEntity<ResyncResponse>(resyncResponse, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity getInternalServerErrorResponse(ResyncResponse resyncResponse) {
        return new ResponseEntity<ResyncResponse>(resyncResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity getOkResponse(ResyncResponse resyncResponse) {
        return new ResponseEntity<ResyncResponse>(resyncResponse, HttpStatus.OK);
    }

}
