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

import org.esbtools.gateway.resync.ResyncResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ResyncResponseTest {

    private ResyncResponse resyncResponse;

    @Before
    public void setUp() throws Exception {
        resyncResponse = new ResyncResponse();
        resyncResponse.setStatus(ResyncResponse.Status.Error);
        resyncResponse.setErrorMessage("error");
    }

    @After
    public void tearDown() throws Exception {
        resyncResponse = null;
    }

    @Test
    public void testGetStatus() throws Exception {
        assertEquals(ResyncResponse.Status.Error, resyncResponse.getStatus());
    }

    @Test
    public void testSetStatus() throws Exception {
        resyncResponse = new ResyncResponse(GatewayResponse.Status.Success, null);
        assertEquals(ResyncResponse.Status.Success, resyncResponse.getStatus());
    }

    @Test
    public void testGetErrorMessage() throws Exception {
        assertEquals("error", resyncResponse.getErrorMessage());
    }

    @Test
    public void testSetErrorMessage() throws Exception {
        resyncResponse = new ResyncResponse(GatewayResponse.Status.Error, "error1");
        assertEquals("error1", resyncResponse.getErrorMessage());
    }

    @Test
    public void testToString() throws Exception {
        assertEquals("ResyncResponse [status=Error, errorMessage=error]", resyncResponse.toString());
    }

    @Test
    public void testToJson() throws Exception {
        assertEquals("{\n" +
                "  \"status\" : \"Error\",\n" +
                "  \"errorMessage\" : \"error\"\n" +
                "}", resyncResponse.toJson());
    }
}
