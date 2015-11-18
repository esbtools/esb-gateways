package org.esbtools.gateway.resync;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/applicationContext.xml" })
public class ResyncServiceTest {

    @Resource(name="resyncService")
    private ResyncService resyncService;

    @Resource(name="badResyncService")
    private ResyncService badResyncService;

    @Before
    public void setupTest() {

    }

    @After
    public void tearDown() {
        resyncService = null;
    }

    @Test
    public void doesRequestWithAllRequiredValuesReturnSuccessfulResponse() throws Exception {
        ResyncRequest request = new ResyncRequest();
        request.setEntity("User");
        request.setSystem("GitHub");
        request.setKey("Login");
        request.setValues(Arrays.asList("derek63","dhaynes"));

        ResyncResponse resyncResponse = resyncService.resync(request);
        assertEquals(ResyncResponse.Status.Success, resyncResponse.getStatus());
    }

    @Test
    public void doesMissingEntityReturnErrorResponse() throws Exception {
        ResyncRequest request = new ResyncRequest();
        request.setSystem("GitHub");
        request.setKey("Login");
        request.setValues(Arrays.asList("derek63","dhaynes"));

        ResyncResponse resyncResponse = resyncService.resync(request);
        assertEquals(ResyncResponse.Status.Error, resyncResponse.getStatus());
    }

    @Test
    public void doesMissingSystemReturnErrorResponse() throws Exception {
        ResyncRequest request = new ResyncRequest();
        request.setEntity("User");
        request.setKey("Login");
        request.setValues(Arrays.asList("derek63","dhaynes"));

        ResyncResponse resyncResponse = resyncService.resync(request);
        assertEquals(ResyncResponse.Status.Error, resyncResponse.getStatus());
    }

    @Test
    public void doesMissingKeyReturnErrorResponse() throws Exception {
        ResyncRequest request = new ResyncRequest();
        request.setEntity("User");
        request.setSystem("GitHub");
        request.setValues(Arrays.asList("derek63","dhaynes"));

        ResyncResponse resyncResponse = resyncService.resync(request);
        assertEquals(ResyncResponse.Status.Error, resyncResponse.getStatus());
    }

    @Test
    public void doesMissingValuesReturnErrorResponse() throws Exception {
        ResyncRequest request = new ResyncRequest();
        request.setEntity("User");
        request.setSystem("GitHub");
        request.setKey("Login");

        ResyncResponse resyncResponse = resyncService.resync(request);
        assertEquals(ResyncResponse.Status.Error, resyncResponse.getStatus());
    }

    @Test
    public void doesSendingUnconfiguredSystemReturnErrorResponse() throws Exception {
        ResyncRequest request = new ResyncRequest();
        request.setEntity("User");
        request.setSystem("BitHub");
        request.setKey("Login");
        request.setValues(Arrays.asList("derek63","dhaynes"));

        ResyncResponse resyncResponse = resyncService.resync(request);
        assertEquals(ResyncResponse.Status.Error, resyncResponse.getStatus());
        assertEquals(String.format("The %s system is not configured properly", request.getSystem()), resyncResponse.getErrorMessage());
    }

    @Test
    public void doesServerErrorReturnErrorResponse() throws Exception {
        ResyncRequest request = new ResyncRequest();
        request.setEntity("User");
        request.setSystem("GitHub");
        request.setKey("Login");
        request.setValues(Arrays.asList("derek63","dhaynes"));

        ResyncResponse resyncResponse = badResyncService.resync(request);
        assertEquals(ResyncResponse.Status.Error, resyncResponse.getStatus());
        assertEquals(String.format("There was a problem enqueuing the selected message: %s", request.toString()), resyncResponse.getErrorMessage());
    }

}
