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
package org.talend.components.processing.definition.replicate;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.talend.daikon.properties.presentation.Form;
import org.talend.daikon.properties.presentation.Widget;

import java.util.Collection;

import static junit.framework.TestCase.assertFalse;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class ReplicatePropertiesTest {

    /**
     * Checks {@link ReplicateProperties} sets correctly initial schema property
     */
    @Test
    public void testDefaultProperties() {
        ReplicateProperties properties = new ReplicateProperties("test");
        // check value
        assertTrue(properties.cacheOutput.getValue());
        assertNull(properties.storageLevel.getValue());
        assertNull(properties.tachyonStoreBaseDir.getValue());
        assertNull(properties.tachyonStoreUrl.getValue());
        assertFalse(properties.compressRDD.getValue());
        assertNull(properties.compressCodec.getValue());

        // check value
        properties.setupProperties();
        assertFalse(properties.cacheOutput.getValue());
        assertEquals(properties.storageLevel.getValue(), StorageLevel.MEMORY_ONLY);
        assertEquals(properties.tachyonStoreBaseDir.getValue(), "/tmp/");
        assertEquals(properties.tachyonStoreUrl.getValue(), "tachyon://localhost:19998");
        assertFalse(properties.compressRDD.getValue());
        assertEquals(properties.compressCodec.getValue(), CompressCodec.SNAPPY);
    }


    /**
     * Checks {@link ReplicateProperties} sets correctly initial layout properties
     */
    @Test
    public void testSetupLayout() {
        ReplicateProperties properties = new ReplicateProperties("test");

        properties.setupLayout();

        Form main = properties.getForm(Form.MAIN);
        assertThat(main, Matchers.notNullValue());

        Collection<Widget> mainWidgets = main.getWidgets();
        assertThat(mainWidgets, Matchers.hasSize(6));
        Widget cacheOutput = main.getWidget("cacheOutput");
        assertThat(cacheOutput, Matchers.notNullValue());
        Widget storageLevel = main.getWidget("storageLevel");
        assertThat(storageLevel, Matchers.notNullValue());
        Widget tachyonStoreBaseDir = main.getWidget("tachyonStoreBaseDir");
        assertThat(tachyonStoreBaseDir, Matchers.notNullValue());
        Widget tachyonStoreUrl = main.getWidget("tachyonStoreUrl");
        assertThat(tachyonStoreUrl, Matchers.notNullValue());
        Widget compressRDD = main.getWidget("compressRDD");
        assertThat(compressRDD, Matchers.notNullValue());
        Widget compressCodec = main.getWidget("compressCodec");
        assertThat(compressCodec, Matchers.notNullValue());
    }

    /**
     * Checks {@link ReplicateProperties#refreshLayout(Form)}
     */
    @Test
    public void testRefreshLayout() {
        ReplicateProperties properties = new ReplicateProperties("test");
        properties.init();
        properties.refreshLayout(properties.getForm(Form.MAIN));

        assertTrue(properties.getForm(Form.MAIN).getWidget("cacheOutput").isVisible());
        assertTrue(properties.getForm(Form.MAIN).getWidget("storageLevel").isHidden());
        assertTrue(properties.getForm(Form.MAIN).getWidget("tachyonStoreBaseDir").isHidden());
        assertTrue(properties.getForm(Form.MAIN).getWidget("tachyonStoreUrl").isHidden());
        assertTrue(properties.getForm(Form.MAIN).getWidget("compressRDD").isHidden());
        assertTrue(properties.getForm(Form.MAIN).getWidget("compressCodec").isHidden());

        properties.cacheOutput.setValue(true);
        properties.refreshLayout(properties.getForm(Form.MAIN));
        assertTrue(properties.getForm(Form.MAIN).getWidget("cacheOutput").isVisible());
        assertTrue(properties.getForm(Form.MAIN).getWidget("storageLevel").isVisible());
        assertTrue(properties.getForm(Form.MAIN).getWidget("tachyonStoreBaseDir").isHidden());
        assertTrue(properties.getForm(Form.MAIN).getWidget("tachyonStoreUrl").isHidden());
        assertTrue(properties.getForm(Form.MAIN).getWidget("compressRDD").isHidden());
        assertTrue(properties.getForm(Form.MAIN).getWidget("compressCodec").isHidden());

        properties.storageLevel.setValue(StorageLevel.MEMORY_ONLY_SER);
        properties.refreshLayout(properties.getForm(Form.MAIN));
        assertTrue(properties.getForm(Form.MAIN).getWidget("cacheOutput").isVisible());
        assertTrue(properties.getForm(Form.MAIN).getWidget("storageLevel").isVisible());
        assertTrue(properties.getForm(Form.MAIN).getWidget("tachyonStoreBaseDir").isHidden());
        assertTrue(properties.getForm(Form.MAIN).getWidget("tachyonStoreUrl").isHidden());
        assertTrue(properties.getForm(Form.MAIN).getWidget("compressRDD").isVisible());
        assertTrue(properties.getForm(Form.MAIN).getWidget("compressCodec").isHidden());

        properties.storageLevel.setValue(StorageLevel.MEMORY_AND_DISK_SER);
        properties.refreshLayout(properties.getForm(Form.MAIN));
        assertTrue(properties.getForm(Form.MAIN).getWidget("cacheOutput").isVisible());
        assertTrue(properties.getForm(Form.MAIN).getWidget("storageLevel").isVisible());
        assertTrue(properties.getForm(Form.MAIN).getWidget("tachyonStoreBaseDir").isHidden());
        assertTrue(properties.getForm(Form.MAIN).getWidget("tachyonStoreUrl").isHidden());
        assertTrue(properties.getForm(Form.MAIN).getWidget("compressRDD").isVisible());
        assertTrue(properties.getForm(Form.MAIN).getWidget("compressCodec").isHidden());

        properties.storageLevel.setValue(StorageLevel.MEMORY_ONLY_SER_2);
        properties.refreshLayout(properties.getForm(Form.MAIN));
        assertTrue(properties.getForm(Form.MAIN).getWidget("cacheOutput").isVisible());
        assertTrue(properties.getForm(Form.MAIN).getWidget("storageLevel").isVisible());
        assertTrue(properties.getForm(Form.MAIN).getWidget("tachyonStoreBaseDir").isHidden());
        assertTrue(properties.getForm(Form.MAIN).getWidget("tachyonStoreUrl").isHidden());
        assertTrue(properties.getForm(Form.MAIN).getWidget("compressRDD").isVisible());
        assertTrue(properties.getForm(Form.MAIN).getWidget("compressCodec").isHidden());

        properties.storageLevel.setValue(StorageLevel.MEMORY_AND_DISK_SER_2);
        properties.refreshLayout(properties.getForm(Form.MAIN));
        assertTrue(properties.getForm(Form.MAIN).getWidget("cacheOutput").isVisible());
        assertTrue(properties.getForm(Form.MAIN).getWidget("storageLevel").isVisible());
        assertTrue(properties.getForm(Form.MAIN).getWidget("tachyonStoreBaseDir").isHidden());
        assertTrue(properties.getForm(Form.MAIN).getWidget("tachyonStoreUrl").isHidden());
        assertTrue(properties.getForm(Form.MAIN).getWidget("compressRDD").isVisible());
        assertTrue(properties.getForm(Form.MAIN).getWidget("compressCodec").isHidden());

        properties.compressRDD.setValue(true);
        properties.storageLevel.setValue(StorageLevel.MEMORY_AND_DISK_SER_2);
        properties.refreshLayout(properties.getForm(Form.MAIN));
        assertTrue(properties.getForm(Form.MAIN).getWidget("cacheOutput").isVisible());
        assertTrue(properties.getForm(Form.MAIN).getWidget("storageLevel").isVisible());
        assertTrue(properties.getForm(Form.MAIN).getWidget("tachyonStoreBaseDir").isHidden());
        assertTrue(properties.getForm(Form.MAIN).getWidget("tachyonStoreUrl").isHidden());
        assertTrue(properties.getForm(Form.MAIN).getWidget("compressRDD").isVisible());
        assertTrue(properties.getForm(Form.MAIN).getWidget("compressCodec").isVisible());

        properties.compressRDD.setValue(true);
        properties.storageLevel.setValue(StorageLevel.MEMORY_ONLY_SER_2);
        properties.refreshLayout(properties.getForm(Form.MAIN));
        assertTrue(properties.getForm(Form.MAIN).getWidget("cacheOutput").isVisible());
        assertTrue(properties.getForm(Form.MAIN).getWidget("storageLevel").isVisible());
        assertTrue(properties.getForm(Form.MAIN).getWidget("tachyonStoreBaseDir").isHidden());
        assertTrue(properties.getForm(Form.MAIN).getWidget("tachyonStoreUrl").isHidden());
        assertTrue(properties.getForm(Form.MAIN).getWidget("compressRDD").isVisible());
        assertTrue(properties.getForm(Form.MAIN).getWidget("compressCodec").isVisible());

        properties.compressRDD.setValue(true);
        properties.storageLevel.setValue(StorageLevel.MEMORY_AND_DISK_SER);
        properties.refreshLayout(properties.getForm(Form.MAIN));
        assertTrue(properties.getForm(Form.MAIN).getWidget("cacheOutput").isVisible());
        assertTrue(properties.getForm(Form.MAIN).getWidget("storageLevel").isVisible());
        assertTrue(properties.getForm(Form.MAIN).getWidget("tachyonStoreBaseDir").isHidden());
        assertTrue(properties.getForm(Form.MAIN).getWidget("tachyonStoreUrl").isHidden());
        assertTrue(properties.getForm(Form.MAIN).getWidget("compressRDD").isVisible());
        assertTrue(properties.getForm(Form.MAIN).getWidget("compressCodec").isVisible());

        properties.compressRDD.setValue(true);
        properties.storageLevel.setValue(StorageLevel.MEMORY_ONLY_SER);
        properties.refreshLayout(properties.getForm(Form.MAIN));
        assertTrue(properties.getForm(Form.MAIN).getWidget("cacheOutput").isVisible());
        assertTrue(properties.getForm(Form.MAIN).getWidget("storageLevel").isVisible());
        assertTrue(properties.getForm(Form.MAIN).getWidget("tachyonStoreBaseDir").isHidden());
        assertTrue(properties.getForm(Form.MAIN).getWidget("tachyonStoreUrl").isHidden());
        assertTrue(properties.getForm(Form.MAIN).getWidget("compressRDD").isVisible());
        assertTrue(properties.getForm(Form.MAIN).getWidget("compressCodec").isVisible());

        properties.compressRDD.setValue(false);
        properties.storageLevel.setValue(StorageLevel.OFF_HEAP);
        properties.refreshLayout(properties.getForm(Form.MAIN));
        assertTrue(properties.getForm(Form.MAIN).getWidget("cacheOutput").isVisible());
        assertTrue(properties.getForm(Form.MAIN).getWidget("storageLevel").isVisible());
        assertTrue(properties.getForm(Form.MAIN).getWidget("tachyonStoreBaseDir").isVisible());
        assertTrue(properties.getForm(Form.MAIN).getWidget("tachyonStoreUrl").isVisible());
        assertTrue(properties.getForm(Form.MAIN).getWidget("compressRDD").isHidden());
        assertTrue(properties.getForm(Form.MAIN).getWidget("compressCodec").isHidden());
    }
}
