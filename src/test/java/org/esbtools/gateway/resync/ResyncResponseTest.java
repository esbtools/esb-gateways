package org.esbtools.gateway.resync;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ResyncResponseTest {

    private ResyncResponse resyncResponse;

    @Before
    public void setUp() throws Exception {
        resyncResponse = new ResyncResponse();
        resyncResponse.setStatus(ResyncResponse.Status.Error);
        resyncResponse.setErrorMessage("error");
    }

    @After
    public void tearDown() throws Exception {
        resyncResponse = null;
    }

    @Test
    public void testGetStatus() throws Exception {
        assertEquals(ResyncResponse.Status.Error, resyncResponse.getStatus());
    }

    @Test
    public void testSetStatus() throws Exception {
        resyncResponse.setStatus(ResyncResponse.Status.Success);
        assertEquals(ResyncResponse.Status.Success, resyncResponse.getStatus());
    }

    @Test
    public void testGetErrorMessage() throws Exception {
        assertEquals("error", resyncResponse.getErrorMessage());
    }

    @Test
    public void testSetErrorMessage() throws Exception {
        resyncResponse.setErrorMessage("error1");
        assertEquals("error1", resyncResponse.getErrorMessage());
    }

    @Test
    public void testToString() throws Exception {
        assertEquals("ResyncResponse [status=Error, errorMessage=error]", resyncResponse.toString());
    }
}