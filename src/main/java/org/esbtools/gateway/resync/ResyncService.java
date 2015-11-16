package org.esbtools.gateway.resync;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.IllegalStateException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.util.LinkedHashMap;

/**
 * Created by dhaynes on 11/13/15.
 */

@Service
@Resource(name="resyncService")
public class ResyncService {

    protected LinkedHashMap<String, ResyncConfiguration> resyncConfigurations;

    protected static final Logger LOGGER= LoggerFactory.getLogger(ResyncService.class);

    public void setResyncConfigurations(LinkedHashMap<String, ResyncConfiguration> resyncConfigurations) {
        this.resyncConfigurations = resyncConfigurations;
    }

    public ResyncResponse resync(ResyncRequest resyncRequest) {
        LOGGER.debug(resyncRequest.toString());

        ResyncResponse resyncResponse = new ResyncResponse();

        if(resyncRequest.hasValuesForRequiredProperties()) {
            enqueue(resyncRequest, resyncResponse);
        } else {
            resyncResponse.setErrorMessage("One or more required values was not present");
            resyncResponse.setStatus(ResyncResponse.Status.Error);
        }

        LOGGER.info(resyncResponse.toString());
        return resyncResponse;
    }

    private ResyncResponse enqueue(final ResyncRequest resyncRequest, ResyncResponse resyncResponse) {
        LOGGER.debug(resyncRequest.toString());

        try {
            getDestination(resyncRequest).send(new MessageCreator() {
                @Override
                public Message createMessage(Session session) throws JMSException {
                    Message message = session.createTextMessage(resyncRequest.toXML());
                    LOGGER.info("Sending message");
                    return message;
                }
            });
            resyncResponse.setStatus(ResyncResponse.Status.Success);
        } catch (IllegalStateException e) {
            LOGGER.error("EndSystem not configured properly {}", resyncRequest.getSystem(), e);
            resyncResponse.setErrorMessage(String.format("The %s system is not configured properly", resyncRequest.getSystem()));
            resyncResponse.setStatus(ResyncResponse.Status.Error);
        } catch (RuntimeException e) {
            LOGGER.error("There was a problem enqueueing the selected message: {}", resyncRequest, e);
            resyncResponse.setErrorMessage(String.format("There was a problem enqueuing the selected message: %s", resyncRequest.toString()));
            resyncResponse.setStatus(ResyncResponse.Status.Error);
        }
        return resyncResponse;
    }

    private JmsTemplate getDestination(final ResyncRequest resyncRequest) {
        ResyncConfiguration resyncConfiguration = resyncConfigurations.get(resyncRequest.getSystem());
        if(null == resyncConfiguration) {
            throw new java.lang.IllegalStateException(String.format("ResyncConfiguration for endSystem %s doesn't exist", resyncRequest.getSystem()));
        }
        return resyncConfigurations.get(resyncRequest.getSystem()).getBroker();
    }

}
