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
        resubmitRequest.setSystem("system");
        resubmitRequest.setPayload("<Payload><Name>Name</Name><Location><City>Berlin</City></Location></Payload>");
        resubmitRequest.setHeaders(Arrays.asList("header1","header2"));
    }

    @After
    public void tearDown() throws Exception {
        resubmitRequest = null;
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
        assertEquals("<Payload><Name>Name</Name><Location><City>Berlin</City></Location></Payload>", resubmitRequest.getPayload());
    }

    @Test
    public void testSetKey() throws Exception {
        resubmitRequest.setPayload("payload1");
        assertEquals("payload1", resubmitRequest.getPayload());
    }

    @Test
    public void testGetValues() throws Exception {
        assertEquals(Arrays.asList("header1","header2"), resubmitRequest.getHeaders());
    }

    @Test
    public void testSetValues() throws Exception {
        resubmitRequest.setHeaders(Arrays.asList("header3","header4"));
        assertEquals(Arrays.asList("header3","header4"), resubmitRequest.getHeaders());
    }

    @Test
    public void testHasValuesForRequiredPropertiesReturns() throws IncompleteRequestException {
        resubmitRequest.ensureRequiredPropertiesHaveValues();
    }

    @Test
    public void testToString() {
        assertEquals("ResubmitRequest [system=system, payload=<Payload><Name>Name</Name><Location><City>Berlin</City></Location></Payload>, headers=[header1, header2]]", resubmitRequest.toString());
    }

    @Test
    public void testToXml() throws Exception {
        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<ResubmitRequest>\n" +
                "    <Header>header1</Header>\n" +
                "    <Header>header2</Header>\n" +
                "    <Payload>&lt;Payload&gt;&lt;Name&gt;Name&lt;/Name&gt;&lt;Location&gt;&lt;City&gt;Berlin&lt;/City&gt;&lt;/Location&gt;&lt;/Payload&gt;</Payload>\n" +
                "    <System>system</System>\n" +
                "</ResubmitRequest>\n", resubmitRequest.toXML());
    }

}