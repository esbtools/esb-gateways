package org.esbtools.gateway.resync.config;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


/**
 * Created by dhaynes on 11/13/15.
 */
public class ResyncConfigurationTest {

    ResyncConfiguration resyncConfiguration;

    @Before
    public void setUp() throws Exception {
        resyncConfiguration = new ResyncConfiguration();
        resyncConfiguration.setBroker("broker");
        resyncConfiguration.setEndSystem("endSystem");
        resyncConfiguration.setResyncQueue("resyncQueue");
    }

    @After
    public void tearDown() throws Exception {
        resyncConfiguration = null;
    }

    @Test
    public void testGetEndSystem() throws Exception {
        assertEquals("endSystem", resyncConfiguration.getEndSystem());
    }

    @Test
    public void testSetEndSystem() throws Exception {
        resyncConfiguration.setEndSystem("endSystem1");
        assertEquals("endSystem1", resyncConfiguration.getEndSystem());
    }

    @Test
    public void testGetBroker() throws Exception {
        assertEquals("broker", resyncConfiguration.getBroker());
    }

    @Test
    public void testSetBroker() throws Exception {
        resyncConfiguration.setBroker("broker1");
        assertEquals("broker1", resyncConfiguration.getBroker());
    }

    @Test
    public void testGetResyncQueue() throws Exception {
        assertEquals("resyncQueue", resyncConfiguration.getResyncQueue());
    }

    @Test
    public void testSetResyncQueue() throws Exception {
        resyncConfiguration.setResyncQueue("resyncQueue1");
        assertEquals("resyncQueue1", resyncConfiguration.getResyncQueue());
    }
}