// ============================================================================
//
// Copyright (C) 2006-2016 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================

package org.talend.components.simplefileio.input;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.junit.Test;
import org.talend.components.api.component.ComponentDefinition;
import org.talend.components.api.component.ConnectorTopology;
import org.talend.daikon.runtime.RuntimeInfo;

/** Unit tests for {@link SimpleFileIoInputDefinition}. */
public class SimpleFileIoInputDefinitionTest {

    /** Instance to test. Definitions are immutable. */
    private final ComponentDefinition def = new SimpleFileIoInputDefinition();

    /** Checks the basic attributes of the definition. */
    @Test
    public void testBasic() {
        assertThat(def.getName(), is("SimpleFileIoInput"));
        assertThat((Object) def.getPropertiesClass(), is(equalTo((Object) SimpleFileIoInputProperties.class)));
        assertThat(def.getSupportedConnectorTopologies(), contains(ConnectorTopology.OUTGOING));
    }

    /** Checks the {@link RuntimeInfo} of the definition. */
    @Test
    public void testRuntimeInfo() {
        RuntimeInfo runtimeInfo = def.getRuntimeInfo(null, null);
        assertThat(runtimeInfo.getRuntimeClassName(), is("org.talend.components.simplefileio.runtime.SimpleFileIoInputRuntime"));
    }
}