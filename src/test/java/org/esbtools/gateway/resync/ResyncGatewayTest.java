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
    public void doesRequestWithAllRequiredValuesReturnSuccessfulResponse() throws Exception {
        ResyncRequest request = new ResyncRequest();
        request.setEntity("User");
        request.setSystem("GitHub");
        request.setKey("Login");
        request.setValues(Arrays.asList("derek63","dhaynes"));

        ResyncResponse resyncResponse = resyncGateway.resync(request);
        assertEquals(ResyncResponse.Status.Success, resyncResponse.getStatus());
    }

    @Test
    public void doesMissingEntityReturnBadRequestResponse() throws Exception {
        ResyncRequest request = new ResyncRequest();
        request.setSystem("GitHub");
        request.setKey("Login");
        request.setValues(Arrays.asList("derek63","dhaynes"));

        ResyncResponse resyncResponse = resyncGateway.resync(request);
        assertEquals(ResyncResponse.Status.Error, resyncResponse.getStatus());
    }

    @Test
    public void doesMissingSystemReturnBadRequestResponse() throws Exception {
        ResyncRequest request = new ResyncRequest();
        request.setEntity("User");
        request.setKey("Login");
        request.setValues(Arrays.asList("derek63","dhaynes"));

        ResyncResponse resyncResponse = resyncGateway.resync(request);
        assertEquals(ResyncResponse.Status.Error, resyncResponse.getStatus());
    }

    @Test
    public void doesMissingKeyReturnBadRequestResponse() throws Exception {
        ResyncRequest request = new ResyncRequest();
        request.setEntity("User");
        request.setSystem("GitHub");
        request.setValues(Arrays.asList("derek63","dhaynes"));

        ResyncResponse resyncResponse = resyncGateway.resync(request);
        assertEquals(ResyncResponse.Status.Error, resyncResponse.getStatus());
    }

    @Test
    public void doesMissingValuesReturnBadRequestResponse() throws Exception {
        ResyncRequest request = new ResyncRequest();
        request.setEntity("User");
        request.setSystem("GitHub");
        request.setKey("Login");

        ResyncResponse resyncResponse = resyncGateway.resync(request);
        assertEquals(ResyncResponse.Status.Error, resyncResponse.getStatus());
    }

    @Test
    public void doesMissingValuesReturnServerErrorResponse() throws Exception {
        ResyncRequest request = new ResyncRequest();

        ResyncResponse resyncResponse = resyncGateway.resync(request);
        assertEquals(ResyncResponse.Status.Error, resyncResponse.getStatus());
    }
    
}
