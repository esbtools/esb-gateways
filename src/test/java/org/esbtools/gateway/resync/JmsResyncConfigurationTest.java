package org.esbtools.gateway.resync;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jms.core.JmsTemplate;

import static org.junit.Assert.assertEquals;


public class JmsResyncConfigurationTest {

    JmsResyncConfiguration jmsResyncConfiguration;
    JmsTemplate jmsTemplate;

    @Before
    public void setUp() throws Exception {
        jmsTemplate = new JmsTemplate();
        jmsResyncConfiguration = new JmsResyncConfiguration();
        jmsResyncConfiguration.setBroker(jmsTemplate);
        jmsResyncConfiguration.setEndSystem("endSystem");
        jmsResyncConfiguration.setResyncQueue("resyncQueue");
    }

    @After
    public void tearDown() throws Exception {
        jmsResyncConfiguration = null;
    }

    @Test
    public void testGetEndSystem() throws Exception {
        assertEquals("endSystem", jmsResyncConfiguration.getEndSystem());
    }

    @Test
    public void testSetEndSystem() throws Exception {
        jmsResyncConfiguration.setEndSystem("endSystem1");
        assertEquals("endSystem1", jmsResyncConfiguration.getEndSystem());
    }

    @Test
    public void testGetBroker() throws Exception {
        assertEquals(jmsTemplate, jmsResyncConfiguration.getBroker());
    }

    @Test
    public void testSetBroker() throws Exception {
        JmsTemplate jmsTemplate = new JmsTemplate();
        jmsResyncConfiguration.setBroker(jmsTemplate);
        assertEquals(jmsTemplate, jmsResyncConfiguration.getBroker());
    }

    @Test
    public void testGetResyncQueue() throws Exception {
        assertEquals("resyncQueue", jmsResyncConfiguration.getResyncQueue());
    }

    @Test
    public void testSetResyncQueue() throws Exception {
        jmsResyncConfiguration.setResyncQueue("resyncQueue1");
        assertEquals("resyncQueue1", jmsResyncConfiguration.getResyncQueue());
    }
}