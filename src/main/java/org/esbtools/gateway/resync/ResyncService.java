package org.esbtools.gateway.resync;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.util.NoSuchElementException;

import static org.esbtools.gateway.resync.ResyncError.ALL_REQUIRED_VALUES_NOT_PRESENT;
import static org.esbtools.gateway.resync.ResyncError.PROBLEM_ENQUEUING;
import static org.esbtools.gateway.resync.ResyncError.SYSTEM_NOT_CONFIGURED;
import static org.esbtools.gateway.resync.ResyncError.withContext;

@Service
public class ResyncService {

    protected ResyncConfigurations resyncConfigurations;

    protected static final Logger LOGGER = LoggerFactory.getLogger(ResyncService.class);

    public ResyncService() {

    }

    @Autowired
    public ResyncService(ResyncConfigurations resyncConfigurations) {
        this.resyncConfigurations = resyncConfigurations;
    }

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
        LOGGER.debug(resyncRequest.toString());

        try {
            LOGGER.info("Sending message {}", resyncRequest.toXML());
            getDestination(resyncRequest).send(new MessageCreator() {
                @Override
                public Message createMessage(Session session) throws JMSException {
                    Message message = session.createTextMessage(resyncRequest.toXML());
                    return message;
                }
            });
            resyncResponse.setStatus(ResyncResponse.Status.Success);
        } catch (NoSuchElementException e) {
            LOGGER.error("The {} system is not configured properly", resyncRequest.getSystem(), e);
            resyncResponse.setErrorMessage(withContext(SYSTEM_NOT_CONFIGURED, resyncRequest.getSystem()));
            resyncResponse.setStatus(ResyncResponse.Status.Error);
        } catch (RuntimeException e) {
            LOGGER.error("There was a problem enqueuing the selected message: {}", resyncRequest, e);
            resyncResponse.setErrorMessage(withContext(PROBLEM_ENQUEUING, resyncRequest.toString()));
            resyncResponse.setStatus(ResyncResponse.Status.Error);
        }
        return resyncResponse;
    }

    private JmsTemplate getDestination(final ResyncRequest resyncRequest) {
        return resyncConfigurations.getBySystem(resyncRequest.getSystem()).getBroker();
    }

}
