package org.talend.components.fake.tfakeinput;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.arrayContaining;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Test;
import org.talend.components.api.component.ComponentImageType;
import org.talend.components.api.component.ConnectorTopology;
import org.talend.components.fake.FakeFamilyDefinition;
import org.talend.daikon.runtime.RuntimeInfo;

public class TFakeInputDefinitionTest {

    private final TFakeInputDefinition definition = new TFakeInputDefinition();

    /**
     * Check {@link TFakeInputDefinition#getFamilies()} returns string array, which contains "Processing"
     */
    @Test
    public void testGetFamilies() {
        String[] families = definition.getFamilies();
        assertThat(families, arrayContaining(FakeFamilyDefinition.NAME));
    }

    /**
     * Check {@link TFakeInputDefinition#getName()} returns "tFilterColumn"
     */
    @Test
    public void testGetName() {
        String componentName = definition.getName();
        assertEquals(componentName, "tFakeInput");
    }

    /**
     * Check {@link TFakeInputDefinition#getPropertyClass()} returns class, which canonical name is
     * "org.talend.components.processing.tfiltercolumn.TtFilterColumnInputProperties"
     */
    @Test
    public void testGetPropertyClass() {
        Class<?> propertyClass = definition.getPropertyClass();
        String canonicalName = propertyClass.getCanonicalName();
        assertThat(canonicalName, equalTo("org.talend.components.fake.tfakeinput.TFakeInputProperties"));
    }

    @Test
    public void testGetPngImagePath() {
        assertEquals("TFakeInput_icon32.png", definition.getPngImagePath(ComponentImageType.PALLETE_ICON_32X32));
    }

    /**
     * Check {@link TFakeInputDefinition#getRuntimeInfo} returns instance of
     * "org.talend.components.processing.runtime.tfiltercolumn.tFilterColumnRuntime"
     */
    @Test
    public void testGetRuntimeInfo() {
        assertNull(definition.getRuntimeInfo(null, ConnectorTopology.INCOMING));
        assertNull(definition.getRuntimeInfo(null, ConnectorTopology.INCOMING_AND_OUTGOING));
        assertNull(definition.getRuntimeInfo(null, ConnectorTopology.NONE));

        RuntimeInfo runtimeInfo = definition.getRuntimeInfo(null, ConnectorTopology.OUTGOING);
        assertEquals("org.talend.components.fake.runtime.tfakeinput.TFakeInputRuntime", runtimeInfo.getRuntimeClassName());
    }

    @Test
    public void testGetSupportedConnectorTopologies() {
        Set<ConnectorTopology> connector = definition.getSupportedConnectorTopologies();
        assertEquals(1, connector.size());
        assertTrue(connector.contains(ConnectorTopology.OUTGOING));
    }

    /**
     * Check {@link TFakeInputDefinition#isSchemaAutoPropagate()} returns <code>false</code>
     */
    @Test
    public void testIsSchemaAutoPropagate() {
        boolean result = definition.isSchemaAutoPropagate();
        assertFalse(result);
    }

}
