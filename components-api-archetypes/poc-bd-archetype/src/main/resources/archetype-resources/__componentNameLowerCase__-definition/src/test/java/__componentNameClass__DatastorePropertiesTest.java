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

package ${package};

import org.junit.Test;
import org.talend.daikon.properties.presentation.Form;
import org.talend.daikon.properties.presentation.Widget;

import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertNull;

public class ${componentNameClass}DatastorePropertiesTest {
    /**
     * Checks {@link ${componentNameClass}DatastoreProperties} sets correctly initial schema
     * property
     */
    @Test
    public void testDefaultProperties() {
    }

    /**
     * Checks {@link ${componentNameClass}DatastoreProperties} sets correctly initial layout
     * properties
     */
    @Test
    public void testSetupLayout() {
        ${componentNameClass}DatastoreProperties properties = new ${componentNameClass}DatastoreProperties("test");

        properties.setupLayout();

        Form main = properties.getForm(Form.MAIN);

        Collection<Widget> mainWidgets = main.getWidgets();
    }

    /**
     * Checks {@link ${componentNameClass}DatastoreProperties} sets correctly layout after refresh
     * properties
     */
    @Test
    public void testRefreshLayout() {
        ${componentNameClass}DatastoreProperties properties = new ${componentNameClass}DatastoreProperties("test");
        properties.init();
        properties.refreshLayout(properties.getForm(Form.MAIN));
    }
}
