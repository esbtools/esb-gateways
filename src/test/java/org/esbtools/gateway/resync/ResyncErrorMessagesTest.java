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
        Assert.assertEquals(ResyncErrorMessages.INVALID_SYSTEM + "system", ResyncErrorMessages.invalidSystem("system"));
    }

    @Test
    public void testIncompleteRequest() throws Exception {
        assertEquals(ResyncErrorMessages.INCOMPLETE_REQUEST + resyncRequest, ResyncErrorMessages.incompleteRequest(resyncRequest));
    }

    @Test
    public void testSystemNotConfigured() throws Exception {
        assertEquals(ResyncErrorMessages.SYSTEM_NOT_CONFIGURED + "system", ResyncErrorMessages.systemNotConfigured("system"));
    }

    @Test
    public void testResyncFailed() throws Exception {
        assertEquals(ResyncErrorMessages.RESYNC_FAILED + resyncRequest, ResyncErrorMessages.resyncFailed(resyncRequest));
    }
}