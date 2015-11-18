package org.esbtools.gateway.resync;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class JmsResyncConfiguration {

    private String endSystem;
    private JmsTemplate broker;
    private String resyncQueue;

    public JmsResyncConfiguration() {

    }

    public JmsResyncConfiguration(String endSystem, JmsTemplate broker, String resyncQueue) {
        this.endSystem = endSystem;
        this.broker = broker;
        this.resyncQueue = resyncQueue;
    }

    public String getEndSystem() {
        return endSystem;
    }

    public void setEndSystem(String endSystem) {
        this.endSystem = endSystem;
    }

    public JmsTemplate getBroker() {
        return broker;
    }

    public void setBroker(JmsTemplate broker) {
        this.broker = broker;
    }

    public String getResyncQueue() {
        return resyncQueue;
    }

    public void setResyncQueue(String resyncQueue) {
        this.resyncQueue = resyncQueue;
    }

}
