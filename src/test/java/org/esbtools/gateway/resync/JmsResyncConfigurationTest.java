/*
 Copyright 2015 esbtools Contributors and/or its affiliates.

 This file is part of esbtools.

 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.esbtools.gateway.resync;

import org.esbtools.gateway.resync.service.JmsResyncConfiguration;
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
        jmsResyncConfiguration = new JmsResyncConfiguration("endSystem", jmsTemplate, "resyncQueue");
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