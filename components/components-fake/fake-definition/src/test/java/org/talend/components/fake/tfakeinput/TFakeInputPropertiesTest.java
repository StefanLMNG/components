package org.talend.components.fake.tfakeinput;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

import org.apache.avro.Schema;
import org.apache.avro.Schema.Field.Order;
import org.junit.Test;
import org.talend.components.api.component.PropertyPathConnector;
import org.talend.daikon.avro.AvroRegistry;
import org.talend.daikon.properties.Properties.Helper;
import org.talend.daikon.properties.presentation.Form;
import org.talend.daikon.properties.presentation.Widget;

public class TFakeInputPropertiesTest {

    /**
     * Checks {@link TFakeInputProperties} sets correctly initial schema property
     */
    @Test
    public void testDefaultProperties() {
        TFakeInputProperties properties = new TFakeInputProperties("test");
        assertNull(properties.schemaFlow.schema.getValue());

        properties.init();

        AvroRegistry registry = new AvroRegistry();
        Schema stringSchema = registry.getConverter(String.class).getSchema();
        Schema.Field inputValue1Field = new Schema.Field("value1", stringSchema, null, null, Order.ASCENDING);
        Schema.Field inputValue2Field = new Schema.Field("value2", stringSchema, null, null, Order.ASCENDING);
        Schema standardSchema = Schema.createRecord("standardSchema", null, null, false,
                Arrays.asList(inputValue1Field, inputValue2Field));

        assertThat(standardSchema, equalTo(properties.schemaFlow.schema.getValue()));
    }

    @Test
    public void testDefaultProperties_serialization() {
        TFakeInputProperties properties = new TFakeInputProperties("test");
        properties.init();

        AvroRegistry registry = new AvroRegistry();
        Schema stringSchema = registry.getConverter(String.class).getSchema();
        Schema.Field inputValue1Field = new Schema.Field("value1", stringSchema, null, null, Order.ASCENDING);
        Schema.Field inputValue2Field = new Schema.Field("value2", stringSchema, null, null, Order.ASCENDING);
        Schema standardSchema = Schema.createRecord("standardSchema", null, null, false,
                Arrays.asList(inputValue1Field, inputValue2Field));

        properties = Helper.fromSerializedPersistent(properties.toSerialized(), TFakeInputProperties.class).object;

        assertThat(standardSchema, equalTo(properties.schemaFlow.schema.getValue()));
    }

    /**
     * Checks {@link TFakeInputProperties#refreshLayout(Form)}
     */
    @Test
    public void testRefreshLayoutMainInitial() {
        TFakeInputProperties properties = new TFakeInputProperties("test");
        properties.init();

        properties.refreshLayout(properties.getForm(Form.MAIN));

        boolean mainIsHidden = properties.getForm(Form.MAIN).getWidget("schemaFlow").isHidden();
        assertFalse(mainIsHidden);
    }

    /**
     * Checks {@link TFakeInputProperties#setupLayout()} creates a main form:
     * 
     * Checks {@link TFakeInputProperties#setupLayout()} creates Main form, which contains 1 widgets and checks widgets
     * names
     */
    @Test
    public void testSetupLayout() {
        TFakeInputProperties properties = new TFakeInputProperties("test");
        properties.schemaFlow.init();

        properties.setupLayout();

        Form main = properties.getForm(Form.MAIN);
        assertThat(main, notNullValue());

        Collection<Widget> mainWidgets = main.getWidgets();
        assertThat(mainWidgets, hasSize(1));
        Widget mainWidget = main.getWidget("schemaFlow");
        assertThat(mainWidget, notNullValue());
    }

    /**
     * Checks {@link TFakeInputProperties#getAllSchemaPropertiesConnectors(boolean)} returns a {@link Set} with the main
     * link, for input
     */
    @Test
    public void testGetAllSchemaPropertiesConnectorsInput() {
        TFakeInputProperties properties = new TFakeInputProperties("test");

        Set<PropertyPathConnector> inputConnectors = properties.getAllSchemaPropertiesConnectors(false);
        assertEquals(0, inputConnectors.size());

    }

    /**
     * Checks {@link TFakeInputProperties#getAllSchemaPropertiesConnectors(boolean)} returns a {@link Set} with the
     * flow_connect link, for output
     */
    @Test
    public void testGetAllSchemaPropertiesConnectorsOutput() {
        TFakeInputProperties properties = new TFakeInputProperties("test");

        Set<PropertyPathConnector> outputConnectors = properties.getAllSchemaPropertiesConnectors(true);
        assertEquals(1, outputConnectors.size());
        assertTrue(outputConnectors.contains(properties.FLOW_CONNECTOR));
    }

}
