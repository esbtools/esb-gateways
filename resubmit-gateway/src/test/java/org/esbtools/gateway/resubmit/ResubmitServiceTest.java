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
import org.esbtools.gateway.exception.ResubmitFailedException;
import org.esbtools.gateway.resubmit.service.ResubmitService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/applicationContext.xml" })
public class ResubmitServiceTest {

    @Autowired
    @Qualifier("gitHubResubmitService")
    private ResubmitService resubmitService;

    @Autowired
    @Qualifier("badHubResubmitService")
    private ResubmitService badResubmitService;

    @Before
    public void setupTest() {

    }

    @After
    public void tearDown() {
        resubmitService = null;
    }

    @Test
    public void doesRequestWithAllRequiredValuesReturnSuccessfulResponse() {
        ResubmitRequest resubmitRequest = new ResubmitRequest();
        resubmitRequest.setSystem("GitHub");
        resubmitRequest.setPayload("Login");
        Map<String, String> headers = new HashMap<>();
        headers.put("gitHubUserId", "derek63");
        headers.put("otherUserId", "dhaynes");
        resubmitRequest.setHeaders(headers);

        ResubmitResponse resubmitResponse = resubmitService.resubmit(resubmitRequest);
        assertEquals(ResubmitResponse.Status.Success, resubmitResponse.getStatus());
        assertEquals(null, resubmitResponse.getErrorMessage());
    }

    @Test(expected = IncompleteRequestException.class)
    public void doesMissingSystemResultInException() {
        ResubmitRequest resubmitRequest = new ResubmitRequest();
        resubmitRequest.setPayload("Login");
        Map<String, String> headers = new HashMap<>();
        headers.put("gitHubUserId", "derek63");
        headers.put("otherUserId", "dhaynes");
        resubmitRequest.setHeaders(headers);

        resubmitService.resubmit(resubmitRequest);
    }

    @Test(expected = IncompleteRequestException.class)
    public void doesMissingPayloadResultInException() {
        ResubmitRequest resubmitRequest = new ResubmitRequest();
        resubmitRequest.setSystem("GitHub");
        Map<String, String> headers = new HashMap<>();
        headers.put("gitHubUserId", "derek63");
        headers.put("otherUserId", "dhaynes");
        resubmitRequest.setHeaders(headers);

        resubmitService.resubmit(resubmitRequest);
    }

    @Test(expected = ResubmitFailedException.class)
    public void doesServerErrorResultInException() {
        ResubmitRequest resubmitRequest = new ResubmitRequest();
        resubmitRequest.setSystem("BadHub");
        resubmitRequest.setPayload("Login");
        Map<String, String> headers = new HashMap<>();
        headers.put("gitHubUserId", "derek63");
        headers.put("otherUserId", "dhaynes");
        resubmitRequest.setHeaders(headers);

        badResubmitService.resubmit(resubmitRequest);
    }

}
