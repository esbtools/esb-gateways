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

import org.esbtools.gateway.resync.exception.IncompleteRequestException;
import org.esbtools.gateway.resync.exception.ResyncFailedException;
import org.esbtools.gateway.resync.service.ResyncService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/applicationContext.xml" })
public class ResyncServiceTest {

    @Autowired
    @Qualifier("gitHubResyncService")
    private ResyncService resyncService;

    @Autowired
    @Qualifier("badHubResyncService")
    private ResyncService badResyncService;

    @Before
    public void setupTest() {

    }

    @After
    public void tearDown() {
        resyncService = null;
    }

    @Test
    public void doesRequestWithAllRequiredValuesReturnSuccessfulResponse() {
        ResyncRequest resyncRequest = new ResyncRequest();
        resyncRequest.setEntity("User");
        resyncRequest.setSystem("GitHub");
        resyncRequest.setKey("Login");
        resyncRequest.setValues(Arrays.asList("derek63","dhaynes"));

        ResyncResponse resyncResponse = resyncService.resync(resyncRequest);
        assertEquals(ResyncResponse.Status.Success, resyncResponse.getStatus());
        assertEquals(null, resyncResponse.getErrorMessage());
    }

    @Test(expected = IncompleteRequestException.class)
    public void doesMissingEntityResultInException() {
        ResyncRequest resyncRequest = new ResyncRequest();
        resyncRequest.setSystem("GitHub");
        resyncRequest.setKey("Login");
        resyncRequest.setValues(Arrays.asList("derek63","dhaynes"));

        resyncService.resync(resyncRequest);
    }

    @Test(expected = IncompleteRequestException.class)
    public void doesMissingSystemResultInException() {
        ResyncRequest resyncRequest = new ResyncRequest();
        resyncRequest.setEntity("User");
        resyncRequest.setKey("Login");
        resyncRequest.setValues(Arrays.asList("derek63","dhaynes"));

        resyncService.resync(resyncRequest);
    }

    @Test(expected = IncompleteRequestException.class)
    public void doesMissingKeyResultInException() {
        ResyncRequest resyncRequest = new ResyncRequest();
        resyncRequest.setEntity("User");
        resyncRequest.setSystem("GitHub");
        resyncRequest.setValues(Arrays.asList("derek63","dhaynes"));

        resyncService.resync(resyncRequest);
    }

    @Test(expected = IncompleteRequestException.class)
    public void doesMissingValuesResultInException() {
        ResyncRequest resyncRequest = new ResyncRequest();
        resyncRequest.setEntity("User");
        resyncRequest.setSystem("GitHub");
        resyncRequest.setKey("Login");

        resyncService.resync(resyncRequest);
    }

    @Test(expected = ResyncFailedException.class)
    public void doesServerErrorResultInException() {
        ResyncRequest resyncRequest = new ResyncRequest();
        resyncRequest.setEntity("User");
        resyncRequest.setSystem("BadHub");
        resyncRequest.setKey("Login");
        resyncRequest.setValues(Arrays.asList("derek63","dhaynes"));

        badResyncService.resync(resyncRequest);
    }

}
