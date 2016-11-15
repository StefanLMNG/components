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

package org.talend.components.simplefileio;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.talend.components.common.datastore.DatastoreDefinition;
import org.talend.daikon.runtime.RuntimeInfo;

/** Unit tests for {@link SimpleFileIoDatastoreDefinition}. */
public class SimpleFileIoDatastoreDefinitionTest {

    /**
     * Instance to test. Definitions are immutable.
     */
    private final DatastoreDefinition<?> def = new SimpleFileIoDatastoreDefinition();

    /**
     * Checks the basic attributes of the definition.
     */
    @Test
    public void testBasic() {
        assertThat(def.getName(), is("SimpleFileIoDatastore"));
        assertThat((Object) def.getPropertiesClass(), is(equalTo((Object) SimpleFileIoDatastoreProperties.class)));
    }

    /**
     * Checks the {@link RuntimeInfo} of the definition.
     */
    @Test
    public void testRuntimeInfo() {
        RuntimeInfo runtimeInfo = def.getRuntimeInfo(null, null);
        assertEquals("org.talend.components.simplefileio.runtime.SimpleFileIoDatastoreRuntime",
                runtimeInfo.getRuntimeClassName());
    }
}
