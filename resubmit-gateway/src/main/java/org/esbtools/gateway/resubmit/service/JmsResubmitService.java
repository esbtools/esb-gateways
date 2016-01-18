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
package org.esbtools.gateway.resubmit.service;

import org.apache.commons.collections4.MapUtils;
import org.esbtools.gateway.GatewayResponse;
import org.esbtools.gateway.exception.ResubmitFailedException;
import org.esbtools.gateway.resubmit.ResubmitRequest;
import org.esbtools.gateway.resubmit.ResubmitResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.util.Map;

@Service
public class JmsResubmitService implements ResubmitService {

    protected static final Logger LOGGER = LoggerFactory.getLogger(JmsResubmitService.class);

    protected JmsResubmitConfiguration jmsResubmitConfiguration;

    @Autowired
    public JmsResubmitService(JmsResubmitConfiguration jmsResubmitConfiguration) {
        this.jmsResubmitConfiguration = jmsResubmitConfiguration;
    }

    public String getSystemName() {
        return jmsResubmitConfiguration.getEndSystem();
    }

    @Override
    public ResubmitResponse resubmit(ResubmitRequest resubmitRequest) {
        LOGGER.info("{}", resubmitRequest);
        resubmitRequest.ensureRequiredPropertiesHaveValues();
        return enqueue(resubmitRequest);
    }

    private ResubmitResponse enqueue(final ResubmitRequest resubmitRequest) {
        ResubmitResponse resubmitResponse = new ResubmitResponse();
        try {
            LOGGER.info("Sending message {}", resubmitRequest.getPayload());
            jmsResubmitConfiguration.getBroker().send(resubmitRequest.getDestination(), new MessageCreator() {
                @Override
                public Message createMessage(Session session) throws JMSException {
                    Message message = session.createTextMessage(resubmitRequest.getPayload());
                    if(MapUtils.isNotEmpty(resubmitRequest.getHeaders())) {
                        for (Map.Entry<String, String> header : resubmitRequest.getHeaders().entrySet()) {
                            LOGGER.info("Adding header key={}, value{}", header.getKey(), header.getValue());
                            message.setStringProperty(header.getKey(), header.getValue());
                        }
                    }
                    return message;
                }
            });
            resubmitResponse.setStatus(GatewayResponse.Status.Success);
        } catch (RuntimeException e) {
            LOGGER.error("An error occurred when enqueuing the resubmit message: {}", resubmitRequest, e);
            throw new ResubmitFailedException(resubmitRequest);
        }
        LOGGER.info("{}", resubmitResponse);
        return resubmitResponse;
    }

}
