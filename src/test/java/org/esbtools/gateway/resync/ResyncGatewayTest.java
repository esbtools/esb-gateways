package org.esbtools.gateway.resync;

import org.esbtools.gateway.resync.rest.ResyncGateway;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.ws.rs.core.Response;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * Created by dhaynes on 11/12/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/applicationContext.xml" })
public class ResyncGatewayTest {

    //@Autowired
    private ResyncGateway resyncGateway;

    @Before
    public void setupTest() {
        resyncGateway = new ResyncGateway();
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

        Response response = resyncGateway.resync(request);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void doesMissingEntityReturnErrorResponse() throws Exception {
        ResyncRequest request = new ResyncRequest();
        request.setSystem("GitHub");
        request.setKey("Login");
        request.setValues(Arrays.asList("derek63","dhaynes"));

        Response response = resyncGateway.resync(request);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    public void doesMissingSystemReturnErrorResponse() throws Exception {
        ResyncRequest request = new ResyncRequest();
        request.setEntity("User");
        request.setKey("Login");
        request.setValues(Arrays.asList("derek63","dhaynes"));

        Response response = resyncGateway.resync(request);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    public void doesMissingKeyReturnErrorResponse() throws Exception {
        ResyncRequest request = new ResyncRequest();
        request.setEntity("User");
        request.setSystem("GitHub");
        request.setValues(Arrays.asList("derek63","dhaynes"));

        Response response = resyncGateway.resync(request);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    public void doesMissingValuesReturnErrorResponse() throws Exception {
        ResyncRequest request = new ResyncRequest();
        request.setEntity("User");
        request.setSystem("GitHub");
        request.setKey("Login");

        Response response = resyncGateway.resync(request);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

}
