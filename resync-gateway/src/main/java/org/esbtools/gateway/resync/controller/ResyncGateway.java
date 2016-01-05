/*
 Copyright 2015 esbtools Contributors and/or its affiliates.

 This file is part of esbtools.

 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.esbtools.gateway.resync.controller;

import org.esbtools.gateway.resync.ResyncRequest;
import org.esbtools.gateway.resync.ResyncResponse;
import org.esbtools.gateway.exception.IncompleteRequestException;
import org.esbtools.gateway.exception.InvalidSystemException;
import org.esbtools.gateway.exception.ResyncFailedException;
import org.esbtools.gateway.exception.SystemConfigurationException;
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
