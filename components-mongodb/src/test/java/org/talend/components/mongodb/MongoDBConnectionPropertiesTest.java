package org.talend.components.mongodb;


import org.hamcrest.Matcher;
import org.junit.Test;
import org.talend.components.api.properties.ComponentProperties;
import org.talend.components.mongodb.connection.TMongoDBConnectionDefinition;
import org.talend.daikon.properties.presentation.Form;
//import org.talend.components.api.test.ComponentTestUtils;
import org.talend.daikon.properties.presentation.Widget;

import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

/**
 * Unit-tests for {@link MongoDBConnectionProperties} class
 */
public class MongoDBConnectionPropertiesTest {
    @Test
    public void testProperties() {
        /*ComponentProperties props = new TMongoDBConnectionDefinition().createProperties();
        //Basic test, it will check i18n also
        ComponentTestUtils.checkSerialize(props, errorCollector);
        assertThat(props.getForm(Form.MAIN).getName(), is(Form.MAIN));
        assertThat(props.getForm(Form.REFERENCE).getName(), is(Form.REFERENCE));
        assertThat(props.getForm("Wizard").getName(), is("Wizard"));*/
    }
}
