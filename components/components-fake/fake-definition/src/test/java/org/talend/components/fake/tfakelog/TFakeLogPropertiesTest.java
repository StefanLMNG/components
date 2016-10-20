package org.talend.components.fake.tfakelog;

import static org.hamcrest.MatcherAssert.assertThat;
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

public class TFakeLogPropertiesTest {

    /**
     * Checks {@link TFakeLogProperties} sets correctly initial schema property
     */
    @Test
    public void testDefaultProperties() {
        TFakeLogProperties properties = new TFakeLogProperties("test");
        assertNull(properties.main.schema.getValue());

        properties.init();

        AvroRegistry registry = new AvroRegistry();
        Schema stringSchema = registry.getConverter(String.class).getSchema();
        Schema.Field inputValue1Field = new Schema.Field("value1", stringSchema, null, null, Order.ASCENDING);
        Schema.Field inputValue2Field = new Schema.Field("value2", stringSchema, null, null, Order.ASCENDING);
        Schema standardSchema = Schema.createRecord("standardSchema", null, null, false,
                Arrays.asList(inputValue1Field, inputValue2Field));
        assertEquals(standardSchema, properties.main.schema.getValue());
    }

    @Test
    public void testDefaultProperties_serialization() {
        TFakeLogProperties properties = new TFakeLogProperties("test");
        assertNull(properties.main.schema.getValue());

        properties.init();

        properties = Helper.fromSerializedPersistent(properties.toSerialized(), TFakeLogProperties.class).object;

        AvroRegistry registry = new AvroRegistry();
        Schema stringSchema = registry.getConverter(String.class).getSchema();
        Schema.Field inputValue1Field = new Schema.Field("value1", stringSchema, null, null, Order.ASCENDING);
        Schema.Field inputValue2Field = new Schema.Field("value2", stringSchema, null, null, Order.ASCENDING);
        Schema standardSchema = Schema.createRecord("standardSchema", null, null, false,
                Arrays.asList(inputValue1Field, inputValue2Field));
        assertEquals(standardSchema, properties.main.schema.getValue());
    }

    /**
     * Checks {@link TFakeLogProperties#refreshLayout(Form)}
     */
    @Test
    public void testRefreshLayoutMainInitial() {
        TFakeLogProperties properties = new TFakeLogProperties("test");
        properties.init();

        properties.refreshLayout(properties.getForm(Form.MAIN));

        assertFalse(properties.getForm(Form.MAIN).getWidget("main").isHidden());

        properties.refreshLayout(properties.getForm(Form.MAIN));
        assertFalse(properties.getForm(Form.MAIN).getWidget("main").isHidden());
    }

    @Test
    public void testSetupLayout() {
        TFakeLogProperties properties = new TFakeLogProperties("test");
        properties.main.init();

        properties.setupLayout();

        Form main = properties.getForm(Form.MAIN);
        assertThat(main, notNullValue());

        Collection<Widget> mainWidgets = main.getWidgets();
        assertThat(mainWidgets, hasSize(1));
        Widget mainWidget = main.getWidget("main");
        assertThat(mainWidget, notNullValue());
    }

    /**
     * Checks {@link TFakeLogProperties#getAllSchemaPropertiesConnectors(boolean)} returns a {@link Set} with the main
     * link, for input
     */
    @Test
    public void testGetAllSchemaPropertiesConnectorsInput() {
        TFakeLogProperties properties = new TFakeLogProperties("test");

        Set<PropertyPathConnector> inputConnectors = properties.getAllSchemaPropertiesConnectors(false);
        assertEquals(1, inputConnectors.size());
        assertTrue(inputConnectors.contains(properties.MAIN_CONNECTOR));

    }

    @Test
    public void testGetAllSchemaPropertiesConnectorsOutput() {
        TFakeLogProperties properties = new TFakeLogProperties("test");

        Set<PropertyPathConnector> outputConnectors = properties.getAllSchemaPropertiesConnectors(true);
        assertEquals(0, outputConnectors.size());
    }

}
