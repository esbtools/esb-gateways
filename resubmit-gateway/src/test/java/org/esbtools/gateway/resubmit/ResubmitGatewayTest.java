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

import org.esbtools.gateway.exception.GatewayErrorMessages;
import org.esbtools.gateway.resubmit.controller.ResubmitGateway;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration("classpath:/rest-servlet.xml")
@ContextConfiguration(locations = { "classpath:/applicationContext.xml" , "classpath:/rest-servlet.xml"})
public class ResubmitGatewayTest {

    private MockMvc mockMvc;

    @Autowired
    private ResubmitGateway resubmitGateway;

    @Before
    public void setupTest() {
        //MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(resubmitGateway).build();
    }

    @After
    public void tearDown() {
        resubmitGateway = null;
    }

    @Test
    public void doesRequestWithAllRequiredValuesReturnSuccessfulResponse() throws Exception {
        ResubmitRequest request = new ResubmitRequest();
        request.setEntity("User");
        request.setSystem("GitHub");
        request.setKey("Login");
        request.setValues(Arrays.asList("derek63","dhaynes"));

        mockMvc.perform(post("/resubmit").content(request.toJson()).contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(successfulResponse()));
    }

    @Test
    public void doesMissingEntityReturnBadRequestResponse() throws Exception {

        ResubmitRequest request = new ResubmitRequest();
        request.setSystem("GitHub");
        request.setKey("Login");
        request.setValues(Arrays.asList("derek63","dhaynes"));

        mockMvc.perform(post("/resubmit").content(request.toJson()).contentType("application/json"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(incompleteRequestResponse(request)));
    }

    @Test
    public void doesMissingSystemReturnBadRequestResponse() throws Exception {
        ResubmitRequest request = new ResubmitRequest();
        request.setEntity("User");
        request.setKey("Login");
        request.setValues(Arrays.asList("derek63","dhaynes"));

        mockMvc.perform(post("/resubmit").content(request.toJson()).contentType("application/json"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(invalidSystem(null)));
    }

    @Test
    public void doesMissingKeyReturnBadRequestResponse() throws Exception {
        ResubmitRequest request = new ResubmitRequest();
        request.setEntity("User");
        request.setSystem("GitHub");
        request.setValues(Arrays.asList("derek63","dhaynes"));

        mockMvc.perform(post("/resubmit").content(request.toJson()).contentType("application/json"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(incompleteRequestResponse(request)));
    }

    @Test
    public void doesMissingValuesReturnBadRequestResponse() throws Exception {
        ResubmitRequest request = new ResubmitRequest();
        request.setEntity("User");
        request.setSystem("GitHub");
        request.setKey("Login");

        mockMvc.perform(post("/resubmit").content(request.toJson()).contentType("application/json"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(incompleteRequestResponse(request)));
    }

    @Test
    public void doesSendingUnconfiguredSystemReturnErrorResponse() throws Exception {
        ResubmitRequest resubmitRequest = new ResubmitRequest();
        resubmitRequest.setEntity("User");
        resubmitRequest.setSystem("BitHub");
        resubmitRequest.setKey("Login");
        resubmitRequest.setValues(Arrays.asList("derek63","dhaynes"));

        mockMvc.perform(post("/resubmit").content(resubmitRequest.toJson()).contentType("application/json"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(systemNotConfigured("BitHub")));
    }

    @Test
    public void doesServerErrorReturnInternalServerErrorResponse() throws Exception {
        ResubmitRequest resubmitRequest = new ResubmitRequest();
        resubmitRequest.setEntity("User");
        resubmitRequest.setSystem("BadHub");
        resubmitRequest.setKey("Login");
        resubmitRequest.setValues(Arrays.asList("derek63","dhaynes"));

        mockMvc.perform(post("/resubmit").content(resubmitRequest.toJson()).contentType("application/json"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(resubmitFailed(resubmitRequest)));
    }

    private String successfulResponse() {
        return new ResubmitResponse(ResubmitResponse.Status.Success, null).toJson();
    }

    private String incompleteRequestResponse(ResubmitRequest resubmitRequest) {
        return new ResubmitResponse(ResubmitResponse.Status.Error, GatewayErrorMessages.incompleteRequest(resubmitRequest)).toJson();
    }

    private String invalidSystem(String systemName) {
        return new ResubmitResponse(ResubmitResponse.Status.Error, GatewayErrorMessages.invalidSystem(systemName)).toJson();
    }

    private String systemNotConfigured(String systemName) {
        return new ResubmitResponse(ResubmitResponse.Status.Error, GatewayErrorMessages.systemNotConfigured(systemName)).toJson();
    }

    private String resubmitFailed(ResubmitRequest resubmitRequest) {
        return new ResubmitResponse(ResubmitResponse.Status.Error, GatewayErrorMessages.resubmitFailed(resubmitRequest)).toJson();
    }
}
