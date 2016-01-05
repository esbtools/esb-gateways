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
package org.esbtools.gateway.resubmit;

import org.esbtools.gateway.resubmit.ResubmitRequest;
import org.esbtools.gateway.exception.IncompleteRequestException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class ResubmitRequestTest {

    private ResubmitRequest resubmitRequest;

    @Before
    public void setUp() throws Exception {
        resubmitRequest = new ResubmitRequest();
        resubmitRequest.setEntity("entity");
        resubmitRequest.setSystem("system");
        resubmitRequest.setKey("key");
        resubmitRequest.setValues(Arrays.asList("value1","value2"));
    }

    @After
    public void tearDown() throws Exception {
        resubmitRequest = null;
    }

    @Test
    public void testGetEntity() throws Exception {
        assertEquals("entity", resubmitRequest.getEntity());
    }

    @Test
    public void testSetEntity() throws Exception {
        resubmitRequest.setEntity("entity1");
        assertEquals("entity1", resubmitRequest.getEntity());
    }

    @Test
    public void testGetSystem() throws Exception {
        assertEquals("system", resubmitRequest.getSystem());
    }

    @Test
    public void testSetSystem() throws Exception {
        resubmitRequest.setSystem("system1");
        assertEquals("system1", resubmitRequest.getSystem());
    }

    @Test
    public void testGetKey() throws Exception {
        assertEquals("key", resubmitRequest.getKey());
    }

    @Test
    public void testSetKey() throws Exception {
        resubmitRequest.setKey("key1");
        assertEquals("key1", resubmitRequest.getKey());
    }

    @Test
    public void testGetValues() throws Exception {
        assertEquals(Arrays.asList("value1","value2"), resubmitRequest.getValues());
    }

    @Test
    public void testSetValues() throws Exception {
        resubmitRequest.setValues(Arrays.asList("value3","value4"));
        assertEquals(Arrays.asList("value3","value4"), resubmitRequest.getValues());
    }

    @Test
    public void testHasValuesForRequiredPropertiesReturns() throws IncompleteRequestException {
        resubmitRequest.ensureRequiredPropertiesHaveValues();
    }

    @Test
    public void testToString() {
        assertEquals("ResubmitRequest [entity=entity, system=system, key=key, values=[value1, value2]]", resubmitRequest.toString());
    }

    @Test
    public void testToXml() throws Exception {
        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<ResubmitRequest>\n" +
                "    <EntityName>entity</EntityName>\n" +
                "    <KeyName>key</KeyName>\n" +
                "    <System>system</System>\n" +
                "    <KeyValue>value1</KeyValue>\n" +
                "    <KeyValue>value2</KeyValue>\n" +
                "</ResubmitRequest>\n", resubmitRequest.toXML());
    }

}