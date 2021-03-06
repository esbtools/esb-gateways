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
package org.esbtools.gateway;

import org.esbtools.gateway.exception.GatewayErrorMessages;
import org.esbtools.gateway.resubmit.ResubmitRequest;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class ResubmitErrorMessagesTest {
    ResubmitRequest resubmitRequest;

    @Before
    public void setUp() {
        resubmitRequest = new ResubmitRequest();
        resubmitRequest.setSystem("system");
        resubmitRequest.setPayload("key");
        Map<String, String> headers = new HashMap<>();
        headers.put("header1", "value1");
        headers.put("header2", "value2");
        resubmitRequest.setHeaders(headers);
    }

    @After
    public void tearDown() {
        resubmitRequest = null;
    }

    @Test
    public void testInvalidSystem() throws Exception {
        Assert.assertEquals("There is no resubmit configuration for this system: " + "system", GatewayErrorMessages.invalidSystem(resubmitRequest));
    }

    @Test
    public void testIncompleteRequest() throws Exception {
        assertEquals("One or more required values was not present: " + resubmitRequest, GatewayErrorMessages.incompleteRequest(resubmitRequest));
    }

    @Test
    public void testSystemNotConfigured() throws Exception {
        assertEquals("One or more systems is not configured correctly: " + "system", GatewayErrorMessages.systemNotConfigured("system"));
    }

    @Test
    public void testResubmitFailed() throws Exception {
        assertEquals("There was a problem resubmiting the selected message: " + resubmitRequest, GatewayErrorMessages.resubmitFailed(resubmitRequest));
    }
}