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

import org.esbtools.gateway.resync.ResyncRequest;
import org.esbtools.gateway.exception.GatewayErrorMessages;
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
        Assert.assertEquals("There is no resync configuration for this system: " + "system", GatewayErrorMessages.invalidSystem(resyncRequest));
    }

    @Test
    public void testIncompleteRequest() throws Exception {
        assertEquals("One or more required values was not present: " + resyncRequest, GatewayErrorMessages.incompleteRequest(resyncRequest));
    }

    @Test
    public void testSystemNotConfigured() throws Exception {
        assertEquals("One or more systems is not configured correctly: " + "system", GatewayErrorMessages.systemNotConfigured("system"));
    }

    @Test
    public void testResyncFailed() throws Exception {
        assertEquals("There was a problem resyncing the selected message: " + resyncRequest, GatewayErrorMessages.resyncFailed(resyncRequest));
    }
}