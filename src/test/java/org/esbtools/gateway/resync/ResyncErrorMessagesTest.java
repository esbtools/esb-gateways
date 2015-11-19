package org.esbtools.gateway.resync;

import org.esbtools.gateway.resync.service.ResyncErrorMessages;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class ResyncErrorMessagesTest {
    ResyncRequest resyncRequest;

    @Before
    public void setUp() {
        resyncRequest = new ResyncRequest();
        resyncRequest.setEntity("entity");
        resyncRequest.setSystem("system");
        resyncRequest.setKey("key");
        resyncRequest.setValues(Arrays.asList("value1","value2"));
    }

    @After
    public void tearDown() {
        resyncRequest = null;
    }

    @Test
    public void testInvalidSystem() throws Exception {
        Assert.assertEquals("There is no resync configuration for this system: " + "system", ResyncErrorMessages.invalidSystem("system"));
    }

    @Test
    public void testIncompleteRequest() throws Exception {
        assertEquals("One or more required values was not present: " + resyncRequest, ResyncErrorMessages.incompleteRequest(resyncRequest));
    }

    @Test
    public void testSystemNotConfigured() throws Exception {
        assertEquals("One or more systems is not configured correctly: " + "system", ResyncErrorMessages.systemNotConfigured("system"));
    }

    @Test
    public void testResyncFailed() throws Exception {
        assertEquals("There was a problem resyncing the selected message: " + resyncRequest, ResyncErrorMessages.resyncFailed(resyncRequest));
    }
}