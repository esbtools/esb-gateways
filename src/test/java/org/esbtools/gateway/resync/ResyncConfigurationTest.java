package org.esbtools.gateway.resync;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jms.core.JmsTemplate;

import static org.junit.Assert.assertEquals;


public class ResyncConfigurationTest {

    ResyncConfiguration resyncConfiguration;
    JmsTemplate jmsTemplate;

    @Before
    public void setUp() throws Exception {
        jmsTemplate = new JmsTemplate();
        resyncConfiguration = new ResyncConfiguration();
        resyncConfiguration.setBroker(jmsTemplate);
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
        assertEquals(jmsTemplate, resyncConfiguration.getBroker());
    }

    @Test
    public void testSetBroker() throws Exception {
        JmsTemplate jmsTemplate = new JmsTemplate();
        resyncConfiguration.setBroker(jmsTemplate);
        assertEquals(jmsTemplate, resyncConfiguration.getBroker());
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