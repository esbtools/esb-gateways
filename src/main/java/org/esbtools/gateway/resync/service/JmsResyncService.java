package org.esbtools.gateway.resync.service;

import org.esbtools.gateway.resync.ResyncRequest;
import org.esbtools.gateway.resync.ResyncResponse;
import org.esbtools.gateway.resync.exception.ResyncFailedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

@Service
public class JmsResyncService implements ResyncService {

    protected static final Logger LOGGER = LoggerFactory.getLogger(JmsResyncService.class);

    protected JmsResyncConfiguration jmsResyncConfiguration;

    @Autowired
    public JmsResyncService(JmsResyncConfiguration jmsResyncConfiguration) {
        this.jmsResyncConfiguration = jmsResyncConfiguration;
    }

    public String getSystemName() {
        return jmsResyncConfiguration.getEndSystem();
    }

    @Override
    public ResyncResponse resync(ResyncRequest resyncRequest) {
        LOGGER.info("{}", resyncRequest);
        resyncRequest.ensureRequiredPropertiesHaveValues();
        return enqueue(resyncRequest);
    }

    private ResyncResponse enqueue(final ResyncRequest resyncRequest) {
        ResyncResponse resyncResponse = new ResyncResponse();
        try {
            LOGGER.info("Sending message {}", resyncRequest.toXML());
            jmsResyncConfiguration.getBroker().send(new MessageCreator() {
                @Override
                public Message createMessage(Session session) throws JMSException {
                    Message message = session.createTextMessage(resyncRequest.toXML());
                    return message;
                }
            });
            resyncResponse.setStatus(ResyncResponse.Status.Success);
        } catch (RuntimeException e) {
            throw new ResyncFailedException(resyncRequest);
        }
        LOGGER.info("{}", resyncResponse);
        return resyncResponse;
    }

}
