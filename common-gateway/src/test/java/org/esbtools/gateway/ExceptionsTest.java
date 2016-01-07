package org.esbtools.gateway;

import org.esbtools.gateway.exception.GatewayErrorMessages;
import org.esbtools.gateway.exception.IncompleteRequestException;
import org.esbtools.gateway.exception.InvalidSystemException;
import org.esbtools.gateway.exception.ResubmitFailedException;
import org.esbtools.gateway.exception.ResyncFailedException;
import org.esbtools.gateway.exception.SystemConfigurationException;
import org.esbtools.gateway.resubmit.ResubmitRequest;
import org.esbtools.gateway.resync.ResyncRequest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ExceptionsTest {

    ResubmitRequest resubmitRequest;
    ResyncRequest resyncRequest;

    @Before
    public void setUp() {
        resubmitRequest = new ResubmitRequest();
        resyncRequest = new ResyncRequest();
    }

    @After
    public void tearDown() {
        resubmitRequest = null;
        resubmitRequest = null;
    }

    @Test
    public void testIncompleteRequestExceptionForResubmit() throws Exception {
        IncompleteRequestException incompleteRequestException = new IncompleteRequestException(resubmitRequest);
        assertTrue(RuntimeException.class.isAssignableFrom(incompleteRequestException.getClass()));
        assertEquals(GatewayErrorMessages.incompleteRequest(resubmitRequest), incompleteRequestException.getMessage());
    }

    @Test
    public void testIncompleteRequestExceptionForResync() throws Exception {
        IncompleteRequestException incompleteRequestException = new IncompleteRequestException(resyncRequest);
        assertTrue(RuntimeException.class.isAssignableFrom(incompleteRequestException.getClass()));
        assertEquals(GatewayErrorMessages.incompleteRequest(resyncRequest), incompleteRequestException.getMessage());
    }

    @Test
    public void testInvalidSystemExceptionForResubmit() throws Exception {
        InvalidSystemException invalidSystemException = new InvalidSystemException(resubmitRequest);
        assertTrue(RuntimeException.class.isAssignableFrom(invalidSystemException.getClass()));
        assertEquals(GatewayErrorMessages.invalidSystem(resubmitRequest), invalidSystemException.getMessage());
    }

    @Test
    public void testInvalidSystemExceptionForResync() throws Exception {
        InvalidSystemException invalidSystemException = new InvalidSystemException(resyncRequest);
        assertTrue(RuntimeException.class.isAssignableFrom(invalidSystemException.getClass()));
        assertEquals(GatewayErrorMessages.invalidSystem(resyncRequest), invalidSystemException.getMessage());
    }

    @Test
    public void testInvalidSystemExceptionForString() throws Exception {
        InvalidSystemException invalidSystemException = new InvalidSystemException("system");
        assertTrue(RuntimeException.class.isAssignableFrom(invalidSystemException.getClass()));
        assertEquals(GatewayErrorMessages.invalidSystem("system"), invalidSystemException.getMessage());
    }

    @Test
    public void testSystemConfigurationException() throws Exception {
        SystemConfigurationException systemConfigurationException = new SystemConfigurationException("system");
        assertTrue(RuntimeException.class.isAssignableFrom(systemConfigurationException.getClass()));
        assertEquals(GatewayErrorMessages.systemNotConfigured("system"), systemConfigurationException.getMessage());
    }

    @Test
    public void testResubmitFailedException() throws Exception {
        ResubmitFailedException resubmitFailedException = new ResubmitFailedException(resubmitRequest);
        assertTrue(RuntimeException.class.isAssignableFrom(resubmitFailedException.getClass()));
        assertEquals(GatewayErrorMessages.resubmitFailed(resubmitRequest), resubmitFailedException.getMessage());
    }

    @Test
    public void testResyncFailedException() throws Exception {
        ResyncFailedException resyncFailedException = new ResyncFailedException(resyncRequest);
        assertTrue(RuntimeException.class.isAssignableFrom(resyncFailedException.getClass()));
        assertEquals(GatewayErrorMessages.resyncFailed(resyncRequest), resyncFailedException.getMessage());
    }

}
