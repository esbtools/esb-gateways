package org.esbtools.gateway.resync;

import org.esbtools.gateway.resync.exception.IncompleteRequestException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class ResyncRequestTest {

    private ResyncRequest resyncRequest;

    @Before
    public void setUp() throws Exception {
        resyncRequest = new ResyncRequest();
        resyncRequest.setEntity("entity");
        resyncRequest.setSystem("system");
        resyncRequest.setKey("key");
        resyncRequest.setValues(Arrays.asList("value1","value2"));
    }

    @After
    public void tearDown() throws Exception {
        resyncRequest = null;
    }

    @Test
    public void testGetEntity() throws Exception {
        assertEquals("entity", resyncRequest.getEntity());
    }

    @Test
    public void testSetEntity() throws Exception {
        resyncRequest.setEntity("entity1");
        assertEquals("entity1", resyncRequest.getEntity());
    }

    @Test
    public void testGetSystem() throws Exception {
        assertEquals("system", resyncRequest.getSystem());
    }

    @Test
    public void testSetSystem() throws Exception {
        resyncRequest.setSystem("system1");
        assertEquals("system1", resyncRequest.getSystem());
    }

    @Test
    public void testGetKey() throws Exception {
        assertEquals("key", resyncRequest.getKey());
    }

    @Test
    public void testSetKey() throws Exception {
        resyncRequest.setKey("key1");
        assertEquals("key1", resyncRequest.getKey());
    }

    @Test
    public void testGetValues() throws Exception {
        assertEquals(Arrays.asList("value1","value2"), resyncRequest.getValues());
    }

    @Test
    public void testSetValues() throws Exception {
        resyncRequest.setValues(Arrays.asList("value3","value4"));
        assertEquals(Arrays.asList("value3","value4"), resyncRequest.getValues());
    }

    @Test
    public void testHasValuesForRequiredPropertiesReturns() throws IncompleteRequestException {
        resyncRequest.ensureRequiredPropertiesHaveValues();
    }

    @Test
    public void testToString() {
        assertEquals("ResyncRequest [entity=entity, system=system, key=key, values=[value1, value2]]", resyncRequest.toString());
    }

    @Test
    public void testToXml() throws Exception {
        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<SyncRequest>\n" +
                "    <EntityName>entity</EntityName>\n" +
                "    <KeyName>key</KeyName>\n" +
                "    <System>system</System>\n" +
                "    <KeyValue>value1</KeyValue>\n" +
                "    <KeyValue>value2</KeyValue>\n" +
                "</SyncRequest>\n", resyncRequest.toXML());
    }

}