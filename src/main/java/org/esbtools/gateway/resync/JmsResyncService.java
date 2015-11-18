package org.esbtools.gateway.resync;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import static org.esbtools.gateway.resync.ResyncError.ALL_REQUIRED_VALUES_NOT_PRESENT;
import static org.esbtools.gateway.resync.ResyncError.PROBLEM_ENQUEUING;
import static org.esbtools.gateway.resync.ResyncError.withContext;

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
        LOGGER.debug("{}", resyncRequest);

        ResyncResponse resyncResponse = new ResyncResponse();

        if (resyncRequest.hasValuesForRequiredProperties()) {
            enqueue(resyncRequest, resyncResponse);
        } else {
            resyncResponse.setErrorMessage(ALL_REQUIRED_VALUES_NOT_PRESENT);
            resyncResponse.setStatus(ResyncResponse.Status.Error);
        }

        LOGGER.info("{}", resyncResponse);
        return resyncResponse;
    }

    private ResyncResponse enqueue(final ResyncRequest resyncRequest, ResyncResponse resyncResponse) {
        LOGGER.debug("{}", resyncRequest);

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
            LOGGER.error("There was a problem enqueuing the selected message: {}", resyncRequest, e);
            resyncResponse.setErrorMessage(withContext(PROBLEM_ENQUEUING, resyncRequest.toString()));
            resyncResponse.setStatus(ResyncResponse.Status.Error);
        }
        return resyncResponse;
    }


}
