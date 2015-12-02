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
package org.esbtools.gateway.resync.service;

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
