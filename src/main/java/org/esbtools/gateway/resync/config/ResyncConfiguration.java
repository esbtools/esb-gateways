package org.esbtools.gateway.resync.config;

/**
 * Created by dhaynes on 11/12/15.
 */
public class ResyncConfiguration {

    private String endSystem;
    private String broker;
    private String resyncQueue;

    public String getEndSystem() {
        return endSystem;
    }

    public void setEndSystem(String endSystem) {
        this.endSystem = endSystem;
    }

    public String getBroker() {
        return broker;
    }

    public void setBroker(String broker) {
        this.broker = broker;
    }

    public String getResyncQueue() {
        return resyncQueue;
    }

    public void setResyncQueue(String resyncQueue) {
        this.resyncQueue = resyncQueue;
    }
}
