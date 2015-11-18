package org.esbtools.gateway.resync;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

import static org.esbtools.gateway.resync.ResyncError.withContext;

import static org.esbtools.gateway.resync.ResyncError.ALL_REQUIRED_VALUES_NOT_PRESENT;
import static org.esbtools.gateway.resync.ResyncError.PROBLEM_ENQUEUING;
import static org.esbtools.gateway.resync.ResyncError.SYSTEM_NOT_CONFIGURED;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/applicationContext.xml" })
public class ResyncServiceTest {

    @Autowired
    private ResyncService resyncService;

    @Before
    public void setupTest() {

    }

    @After
    public void tearDown() {
        resyncService = null;
    }

    @Test
    public void doesRequestWithAllRequiredValuesReturnSuccessfulResponse() throws Exception {
        ResyncRequest resyncRequest = new ResyncRequest();
        resyncRequest.setEntity("User");
        resyncRequest.setSystem("GitHub");
        resyncRequest.setKey("Login");
        resyncRequest.setValues(Arrays.asList("derek63","dhaynes"));

        ResyncResponse resyncResponse = resyncService.resync(resyncRequest);
        assertEquals(ResyncResponse.Status.Success, resyncResponse.getStatus());
        assertEquals(null, resyncResponse.getErrorMessage());
    }

    @Test
    public void doesMissingEntityReturnErrorResponse() throws Exception {
        ResyncRequest resyncRequest = new ResyncRequest();
        resyncRequest.setSystem("GitHub");
        resyncRequest.setKey("Login");
        resyncRequest.setValues(Arrays.asList("derek63","dhaynes"));

        ResyncResponse resyncResponse = resyncService.resync(resyncRequest);
        assertEquals(ResyncResponse.Status.Error, resyncResponse.getStatus());
        assertEquals(withContext(ALL_REQUIRED_VALUES_NOT_PRESENT, resyncRequest.getSystem()), resyncResponse.getErrorMessage());
    }

    @Test
    public void doesMissingSystemReturnErrorResponse() throws Exception {
        ResyncRequest resyncRequest = new ResyncRequest();
        resyncRequest.setEntity("User");
        resyncRequest.setKey("Login");
        resyncRequest.setValues(Arrays.asList("derek63","dhaynes"));

        ResyncResponse resyncResponse = resyncService.resync(resyncRequest);
        assertEquals(ResyncResponse.Status.Error, resyncResponse.getStatus());
        assertEquals(withContext(ALL_REQUIRED_VALUES_NOT_PRESENT, resyncRequest.getSystem()), resyncResponse.getErrorMessage());
    }

    @Test
    public void doesMissingKeyReturnErrorResponse() throws Exception {
        ResyncRequest resyncRequest = new ResyncRequest();
        resyncRequest.setEntity("User");
        resyncRequest.setSystem("GitHub");
        resyncRequest.setValues(Arrays.asList("derek63","dhaynes"));

        ResyncResponse resyncResponse = resyncService.resync(resyncRequest);
        assertEquals(ResyncResponse.Status.Error, resyncResponse.getStatus());
        assertEquals(withContext(ALL_REQUIRED_VALUES_NOT_PRESENT, resyncRequest.getSystem()), resyncResponse.getErrorMessage());
    }

    @Test
    public void doesMissingValuesReturnErrorResponse() throws Exception {
        ResyncRequest resyncRequest = new ResyncRequest();
        resyncRequest.setEntity("User");
        resyncRequest.setSystem("GitHub");
        resyncRequest.setKey("Login");

        ResyncResponse resyncResponse = resyncService.resync(resyncRequest);
        assertEquals(ResyncResponse.Status.Error, resyncResponse.getStatus());
        assertEquals(withContext(ALL_REQUIRED_VALUES_NOT_PRESENT, resyncRequest.getSystem()), resyncResponse.getErrorMessage());
    }

    @Test
    public void doesSendingUnconfiguredSystemReturnErrorResponse() throws Exception {
        ResyncRequest resyncRequest = new ResyncRequest();
        resyncRequest.setEntity("User");
        resyncRequest.setSystem("BitHub");
        resyncRequest.setKey("Login");
        resyncRequest.setValues(Arrays.asList("derek63","dhaynes"));

        ResyncResponse resyncResponse = resyncService.resync(resyncRequest);
        assertEquals(ResyncResponse.Status.Error, resyncResponse.getStatus());
        assertEquals(withContext(SYSTEM_NOT_CONFIGURED, resyncRequest.getSystem()), resyncResponse.getErrorMessage());
    }

    @Test
    public void doesServerErrorReturnErrorResponse() throws Exception {
        ResyncRequest resyncRequest = new ResyncRequest();
        resyncRequest.setEntity("User");
        resyncRequest.setSystem("BadHub");
        resyncRequest.setKey("Login");
        resyncRequest.setValues(Arrays.asList("derek63","dhaynes"));

        ResyncResponse resyncResponse = resyncService.resync(resyncRequest);
        assertEquals(ResyncResponse.Status.Error, resyncResponse.getStatus());
        assertEquals(withContext(PROBLEM_ENQUEUING, resyncRequest.toString()), resyncResponse.getErrorMessage());
    }

}
