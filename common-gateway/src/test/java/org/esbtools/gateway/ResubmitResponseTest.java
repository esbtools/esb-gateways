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

import org.esbtools.gateway.resubmit.ResubmitResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ResubmitResponseTest {

    private ResubmitResponse resubmitResponse;

    @Before
    public void setUp() throws Exception {
        resubmitResponse = new ResubmitResponse();
        resubmitResponse.setStatus(ResubmitResponse.Status.Error);
        resubmitResponse.setErrorMessage("error");
    }

    @After
    public void tearDown() throws Exception {
        resubmitResponse = null;
    }

    @Test
    public void testGetStatus() throws Exception {
        assertEquals(ResubmitResponse.Status.Error, resubmitResponse.getStatus());
    }

    @Test
    public void testSetStatus() throws Exception {
        resubmitResponse.setStatus(ResubmitResponse.Status.Success);
        assertEquals(ResubmitResponse.Status.Success, resubmitResponse.getStatus());
    }

    @Test
    public void testGetErrorMessage() throws Exception {
        assertEquals("error", resubmitResponse.getErrorMessage());
    }

    @Test
    public void testSetErrorMessage() throws Exception {
        resubmitResponse.setErrorMessage("error1");
        assertEquals("error1", resubmitResponse.getErrorMessage());
    }

    @Test
    public void testToString() throws Exception {
        assertEquals("ResubmitResponse [status=Error, errorMessage=error]", resubmitResponse.toString());
    }
}