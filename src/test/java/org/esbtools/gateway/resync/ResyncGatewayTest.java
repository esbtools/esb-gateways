package org.esbtools.gateway.resync;

import org.esbtools.gateway.resync.controller.ResyncGateway;
import org.esbtools.gateway.resync.service.ResyncErrorMessages;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/applicationContext.xml" })
public class ResyncGatewayTest {

    @Autowired
    private ResyncGateway resyncGateway;

    @Before
    public void setupTest() {

    }

    @After
    public void tearDown() {
        resyncGateway = null;
    }

    @Test
    public void doesRequestWithAllRequiredValuesReturnSuccessfulResponse() {
        ResyncRequest request = new ResyncRequest();
        request.setEntity("User");
        request.setSystem("GitHub");
        request.setKey("Login");
        request.setValues(Arrays.asList("derek63","dhaynes"));

        ResponseEntity<ResyncResponse> responseEntity = resyncGateway.resync(request);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        ResyncResponse resyncResponse = responseEntity.getBody();
        assertEquals(ResyncResponse.Status.Success, resyncResponse.getStatus());
    }

    @Test
    public void doesMissingEntityReturnBadRequestResponse() {
        ResyncRequest request = new ResyncRequest();
        request.setSystem("GitHub");
        request.setKey("Login");
        request.setValues(Arrays.asList("derek63","dhaynes"));

        ResponseEntity<ResyncResponse> responseEntity = resyncGateway.resync(request);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

        ResyncResponse resyncResponse = responseEntity.getBody();
        assertEquals(ResyncResponse.Status.Error, resyncResponse.getStatus());
    }

    @Test
    public void doesMissingSystemReturnBadRequestResponse() {
        ResyncRequest request = new ResyncRequest();
        request.setEntity("User");
        request.setKey("Login");
        request.setValues(Arrays.asList("derek63","dhaynes"));

        ResponseEntity<ResyncResponse> responseEntity = resyncGateway.resync(request);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

        ResyncResponse resyncResponse = responseEntity.getBody();
        assertEquals(ResyncResponse.Status.Error, resyncResponse.getStatus());
    }

    @Test
    public void doesMissingKeyReturnBadRequestResponse() {
        ResyncRequest request = new ResyncRequest();
        request.setEntity("User");
        request.setSystem("GitHub");
        request.setValues(Arrays.asList("derek63","dhaynes"));

        ResponseEntity<ResyncResponse> responseEntity = resyncGateway.resync(request);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

        ResyncResponse resyncResponse = responseEntity.getBody();
        assertEquals(ResyncResponse.Status.Error, resyncResponse.getStatus());
    }

    @Test
    public void doesMissingValuesReturnBadRequestResponse() {
        ResyncRequest request = new ResyncRequest();
        request.setEntity("User");
        request.setSystem("GitHub");
        request.setKey("Login");

        ResponseEntity<ResyncResponse> responseEntity = resyncGateway.resync(request);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

        ResyncResponse resyncResponse = responseEntity.getBody();
        assertEquals(ResyncResponse.Status.Error, resyncResponse.getStatus());
    }

    @Test
    public void doesSendingUnconfiguredSystemReturnErrorResponse() {
        ResyncRequest resyncRequest = new ResyncRequest();
        resyncRequest.setEntity("User");
        resyncRequest.setSystem("BitHub");
        resyncRequest.setKey("Login");
        resyncRequest.setValues(Arrays.asList("derek63","dhaynes"));

        ResponseEntity<ResyncResponse> responseEntity = resyncGateway.resync(resyncRequest);
        ResyncResponse resyncResponse = responseEntity.getBody();

        assertEquals(ResyncResponse.Status.Error, resyncResponse.getStatus());
        assertEquals(ResyncErrorMessages.systemNotConfigured("BitHub"), resyncResponse.getErrorMessage());
    }

    public void doesServerErrorReturnInternalServerErrorResponse() {
        ResyncRequest request = new ResyncRequest();
        request.setEntity("User");
        request.setSystem("BadHub");
        request.setKey("Login");
        request.setValues(Arrays.asList("derek63","dhaynes"));

        ResponseEntity<ResyncResponse> responseEntity = resyncGateway.resync(request);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());

        ResyncResponse resyncResponse = responseEntity.getBody();
        assertEquals(ResyncResponse.Status.Error, resyncResponse.getStatus());
        assertEquals(ResyncErrorMessages.resyncFailed(request), resyncResponse.getErrorMessage());
    }
}
