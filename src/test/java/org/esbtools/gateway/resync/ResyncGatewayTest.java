package org.esbtools.gateway.resync;

import org.esbtools.gateway.resync.controller.ResyncGateway;
import org.esbtools.gateway.resync.service.ResyncErrorMessages;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration("classpath:/rest-servlet.xml")
@ContextConfiguration(locations = { "classpath:/applicationContext.xml" , "classpath:/rest-servlet.xml"})
public class ResyncGatewayTest {

    private MockMvc mockMvc;

    @Autowired
    private ResyncGateway resyncGateway;

    @Before
    public void setupTest() {
        //MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(resyncGateway).build();
    }

    @After
    public void tearDown() {
        resyncGateway = null;
    }

    @Test
    public void doesRequestWithAllRequiredValuesReturnSuccessfulResponse() {
        ResyncRequest request = new ResyncRequest();
        request.setEntity("User");
        request.setSystem("GitHub");
        request.setKey("Login");
        request.setValues(Arrays.asList("derek63","dhaynes"));

        ResponseEntity<ResyncResponse> responseEntity = resyncGateway.resync(request);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        ResyncResponse resyncResponse = responseEntity.getBody();
        assertEquals(ResyncResponse.Status.Success, resyncResponse.getStatus());
    }

    @Test
    public void doesMissingEntityReturnBadRequestResponse() throws Exception {

        ResyncRequest request = new ResyncRequest();
        request.setSystem("GitHub");
        request.setKey("Login");
        request.setValues(Arrays.asList("derek63","dhaynes"));

        mockMvc.perform(post("/resync").content(request.toJson()).contentType("application/json"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(incompleteRequestResponse(request)));
    }

    @Test
    public void doesMissingSystemReturnBadRequestResponse() throws Exception {
        ResyncRequest request = new ResyncRequest();
        request.setEntity("User");
        request.setKey("Login");
        request.setValues(Arrays.asList("derek63","dhaynes"));

        mockMvc.perform(post("/resync").content(request.toJson()).contentType("application/json"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(invalidSystem(null)));
    }

    @Test
    public void doesMissingKeyReturnBadRequestResponse() throws Exception {
        ResyncRequest request = new ResyncRequest();
        request.setEntity("User");
        request.setSystem("GitHub");
        request.setValues(Arrays.asList("derek63","dhaynes"));

        mockMvc.perform(post("/resync").content(request.toJson()).contentType("application/json"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(incompleteRequestResponse(request)));
    }

    @Test
    public void doesMissingValuesReturnBadRequestResponse() throws Exception {
        ResyncRequest request = new ResyncRequest();
        request.setEntity("User");
        request.setSystem("GitHub");
        request.setKey("Login");

        mockMvc.perform(post("/resync").content(request.toJson()).contentType("application/json"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(incompleteRequestResponse(request)));
    }

    @Test
    public void doesSendingUnconfiguredSystemReturnErrorResponse() throws Exception {
        ResyncRequest resyncRequest = new ResyncRequest();
        resyncRequest.setEntity("User");
        resyncRequest.setSystem("BitHub");
        resyncRequest.setKey("Login");
        resyncRequest.setValues(Arrays.asList("derek63","dhaynes"));

        mockMvc.perform(post("/resync").content(resyncRequest.toJson()).contentType("application/json"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(systemNotConfigured("BitHub")));
    }


    public void doesServerErrorReturnInternalServerErrorResponse() throws Exception {
        ResyncRequest resyncRequest = new ResyncRequest();
        resyncRequest.setEntity("User");
        resyncRequest.setSystem("BadHub");
        resyncRequest.setKey("Login");
        resyncRequest.setValues(Arrays.asList("derek63","dhaynes"));

        mockMvc.perform(post("/resync").content(resyncRequest.toJson()).contentType("application/json"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(resyncFailed(resyncRequest)));
    }

    private String incompleteRequestResponse(ResyncRequest resyncRequest) {
        return new ResyncResponse(ResyncResponse.Status.Error, ResyncErrorMessages.incompleteRequest(resyncRequest)).toJson();
    }

    private String invalidSystem(String systemName) {
        return new ResyncResponse(ResyncResponse.Status.Error, ResyncErrorMessages.invalidSystem(systemName)).toJson();
    }

    private String systemNotConfigured(String systemName) {
        return new ResyncResponse(ResyncResponse.Status.Error, ResyncErrorMessages.systemNotConfigured(systemName)).toJson();
    }

    private String resyncFailed(ResyncRequest resyncRequest) {
        return new ResyncResponse(ResyncResponse.Status.Error, ResyncErrorMessages.resyncFailed(resyncRequest)).toJson();
    }
}
