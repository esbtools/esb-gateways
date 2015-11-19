package org.esbtools.gateway.resync.controller;

import org.esbtools.gateway.resync.ResyncRequest;
import org.esbtools.gateway.resync.ResyncResponse;
import org.esbtools.gateway.resync.exception.IncompleteRequestException;
import org.esbtools.gateway.resync.exception.InvalidSystemException;
import org.esbtools.gateway.resync.exception.ResyncFailedException;
import org.esbtools.gateway.resync.exception.SystemConfigurationException;
import org.esbtools.gateway.resync.service.ResyncService;
import org.esbtools.gateway.resync.service.ResyncServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class ResyncGateway {

    protected ResyncServiceRepository resyncServiceRepository;

    @Autowired
    public ResyncGateway(ResyncServiceRepository resyncServiceRepository) {
        this.resyncServiceRepository = resyncServiceRepository;
    }

    @RequestMapping(value="/resync", method=RequestMethod.POST, produces= MediaType.APPLICATION_JSON_VALUE, headers = "content-type=application/json")
    public ResponseEntity<ResyncResponse> resync(@RequestBody ResyncRequest resyncRequest) {
        ResyncService resyncService = resyncServiceRepository.getBySystem(resyncRequest.getSystem());
        ResyncResponse resyncResponse = resyncService.resync(resyncRequest);
        return new ResponseEntity<>(resyncResponse, HttpStatus.OK);
    }

    @ExceptionHandler(InvalidSystemException.class)
    private ResponseEntity<ResyncResponse> invalidSystemExceptionHandler (InvalidSystemException e) {
        ResyncResponse resyncResponse = new ResyncResponse(ResyncResponse.Status.Error, e.getMessage());
        return new ResponseEntity<>(resyncResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SystemConfigurationException.class)
    private ResponseEntity<ResyncResponse> systemConfigurationExceptionHandler (SystemConfigurationException e) {
        ResyncResponse resyncResponse = new ResyncResponse(ResyncResponse.Status.Error, e.getMessage());
        return new ResponseEntity<>(resyncResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResyncFailedException.class)
    private ResponseEntity<ResyncResponse> resyncFailedExceptionHandler (ResyncFailedException e) {
        ResyncResponse resyncResponse = new ResyncResponse(ResyncResponse.Status.Error, e.getMessage());
        return new ResponseEntity<>(resyncResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IncompleteRequestException.class)
    private ResponseEntity<ResyncResponse> incompleteRequestExceptionHandler (IncompleteRequestException e) {
        ResyncResponse resyncResponse = new ResyncResponse(ResyncResponse.Status.Error, e.getMessage());
        return new ResponseEntity<>(resyncResponse, HttpStatus.BAD_REQUEST);
    }

}
