package org.esbtools.gateway.resync;

import org.junit.Test;

import static org.esbtools.gateway.resync.ResyncError.withContext;
import static org.junit.Assert.assertEquals;

public class ResyncErrorTest {

    @Test
    public void testWithContext() throws Exception {

        assertEquals("The GitHub system is not configured properly", withContext(ResyncError.SYSTEM_NOT_CONFIGURED, "GitHub"));
        assertEquals("There was a problem enqueuing the selected message: myMessage", withContext(ResyncError.PROBLEM_ENQUEUING, "myMessage"));
        assertEquals("One or more required values was not present", withContext(ResyncError.ALL_REQUIRED_VALUES_NOT_PRESENT, ""));
    }

}