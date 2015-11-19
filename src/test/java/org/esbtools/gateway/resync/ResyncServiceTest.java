package org.esbtools.gateway.resync;

import org.esbtools.gateway.resync.exception.IncompleteRequestException;
import org.esbtools.gateway.resync.exception.ResyncFailedException;
import org.esbtools.gateway.resync.service.ResyncService;
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

    @Resource(name="gitHubResyncService")
    private ResyncService resyncService;

    @Resource(name="badHubResyncService")
    private ResyncService badResyncService;

    @Before
    public void setupTest() {

    }

    @After
    public void tearDown() {
        resyncService = null;
    }

    @Test
    public void doesRequestWithAllRequiredValuesReturnSuccessfulResponse() {
        ResyncRequest resyncRequest = new ResyncRequest();
        resyncRequest.setEntity("User");
        resyncRequest.setSystem("GitHub");
        resyncRequest.setKey("Login");
        resyncRequest.setValues(Arrays.asList("derek63","dhaynes"));

        ResyncResponse resyncResponse = resyncService.resync(resyncRequest);
        assertEquals(ResyncResponse.Status.Success, resyncResponse.getStatus());
        assertEquals(null, resyncResponse.getErrorMessage());
    }

    @Test(expected = IncompleteRequestException.class)
    public void doesMissingEntityResultInException() {
        ResyncRequest resyncRequest = new ResyncRequest();
        resyncRequest.setSystem("GitHub");
        resyncRequest.setKey("Login");
        resyncRequest.setValues(Arrays.asList("derek63","dhaynes"));

        resyncService.resync(resyncRequest);
    }

    @Test(expected = IncompleteRequestException.class)
    public void doesMissingSystemResultInException() {
        ResyncRequest resyncRequest = new ResyncRequest();
        resyncRequest.setEntity("User");
        resyncRequest.setKey("Login");
        resyncRequest.setValues(Arrays.asList("derek63","dhaynes"));

        resyncService.resync(resyncRequest);
    }

    @Test(expected = IncompleteRequestException.class)
    public void doesMissingKeyResultInException() {
        ResyncRequest resyncRequest = new ResyncRequest();
        resyncRequest.setEntity("User");
        resyncRequest.setSystem("GitHub");
        resyncRequest.setValues(Arrays.asList("derek63","dhaynes"));

        resyncService.resync(resyncRequest);
    }

    @Test(expected = IncompleteRequestException.class)
    public void doesMissingValuesResultInException() {
        ResyncRequest resyncRequest = new ResyncRequest();
        resyncRequest.setEntity("User");
        resyncRequest.setSystem("GitHub");
        resyncRequest.setKey("Login");

        resyncService.resync(resyncRequest);
    }

    @Test(expected = ResyncFailedException.class)
    public void doesServerErrorResultInException() {
        ResyncRequest resyncRequest = new ResyncRequest();
        resyncRequest.setEntity("User");
        resyncRequest.setSystem("BadHub");
        resyncRequest.setKey("Login");
        resyncRequest.setValues(Arrays.asList("derek63","dhaynes"));

        badResyncService.resync(resyncRequest);
    }

}
