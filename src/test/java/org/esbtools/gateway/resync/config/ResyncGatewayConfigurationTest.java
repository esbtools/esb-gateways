package org.esbtools.gateway.resync.config;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by dhaynes on 11/13/15.
 */
public class ResyncGatewayConfigurationTest {

    ResyncGatewayConfiguration resyncGatewayConfiguration;
    List<ResyncConfiguration> resyncConfigurations;

    @Before
    public void setUp() {
        resyncGatewayConfiguration = new ResyncGatewayConfiguration();
        ResyncConfiguration resyncConfiguration = new ResyncConfiguration();
        resyncConfiguration.setBroker("broker");
        resyncConfiguration.setEndSystem("endSystem");
        resyncConfiguration.setResyncQueue("resyncQueue");
        resyncConfigurations = new ArrayList<>();
        resyncConfigurations.add(resyncConfiguration);
        resyncGatewayConfiguration.setResyncConfigurations(resyncConfigurations);
    }

    @After
    public void tearDown() {
        resyncGatewayConfiguration = null;
    }

    @Test
    public void testGetResyncConfigurations() throws Exception {
        assertEquals(resyncConfigurations, resyncGatewayConfiguration.getResyncConfigurations());
    }

    @Test
    public void testSetResyncConfigurations() throws Exception {
        ResyncConfiguration resyncConfiguration = new ResyncConfiguration();
        resyncConfiguration.setBroker("broker1");
        resyncConfiguration.setEndSystem("endSystem1");
        resyncConfiguration.setResyncQueue("resyncQueue1");
        List<ResyncConfiguration> resyncConfigurations = new ArrayList<>();
        resyncConfigurations.add(resyncConfiguration);
        resyncGatewayConfiguration.setResyncConfigurations(resyncConfigurations);

        assertTrue(compareResyncConfigurations(resyncConfigurations, resyncGatewayConfiguration.getResyncConfigurations()));
    }

    private boolean compareResyncConfigurations (List<ResyncConfiguration> resyncConfigurations1, List<ResyncConfiguration> resyncConfigurations2) {
        if(resyncConfigurations1.size() == resyncConfigurations2.size()) {
            int i = 0;
            for(ResyncConfiguration resyncConfiguration :resyncConfigurations1) {
                boolean result = compareResyncConfiguration(resyncConfiguration, resyncConfigurations2.get(i));
                if(result == false) {
                    return false;
                }
                i++;
            }
            return true;
        } else {
            return false;
        }
    }

    private boolean compareResyncConfiguration(ResyncConfiguration resyncConfiguration1, ResyncConfiguration resyncConfiguration2) {
        if(resyncConfiguration1.getBroker().equals(resyncConfiguration2.getBroker()) &&
                resyncConfiguration1.getEndSystem().equals(resyncConfiguration2.getEndSystem()) &&
                resyncConfiguration1.getResyncQueue().equals(resyncConfiguration2.getResyncQueue())) {
            return true;
        } else {
            return false;
        }
    }
}