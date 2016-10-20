// ============================================================================
//
// Copyright (C) 2006-2015 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.components.fake.tfakeinput;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.avro.Schema;
import org.apache.avro.Schema.Field.Order;
import org.talend.components.api.component.Connector;
import org.talend.components.api.component.PropertyPathConnector;
import org.talend.components.common.FixedConnectorsComponentProperties;
import org.talend.components.common.SchemaProperties;
import org.talend.daikon.avro.AvroRegistry;
import org.talend.daikon.properties.presentation.Form;

public class TFakeInputProperties extends FixedConnectorsComponentProperties implements Serializable {

	public TFakeInputProperties(String name) {
		super(name);
	}

	public transient PropertyPathConnector FLOW_CONNECTOR = new PropertyPathConnector(Connector.MAIN_NAME,
			"schemaFlow");

	public SchemaProperties schemaFlow = new SchemaProperties("schemaFlow");

	@Override
	public void setupLayout() {
		super.setupLayout();
		Form mainForm = new Form(this, Form.MAIN);
		mainForm.addRow(schemaFlow.getForm(Form.REFERENCE));
	}

	@Override
	public void setupProperties() {
		super.setupProperties();

        AvroRegistry registry = new AvroRegistry();
        Schema stringSchema = registry.getConverter(String.class).getSchema();
        Schema.Field inputValue1Field = new Schema.Field("value1", stringSchema, null, null, Order.ASCENDING);
        Schema.Field inputValue2Field = new Schema.Field("value2", stringSchema, null, null, Order.ASCENDING);
        Schema standardSchema = Schema.createRecord("standardSchema", null, null, false,
                Arrays.asList(inputValue1Field, inputValue2Field));
		schemaFlow.schema.setValue(standardSchema);
	}

	@Override
	public void refreshLayout(Form form) {
		super.refreshLayout(form);
		// everything is always visible
	}

	@Override
	protected Set<PropertyPathConnector> getAllSchemaPropertiesConnectors(boolean isOutputConnection) {
		HashSet<PropertyPathConnector> connectors = new HashSet<PropertyPathConnector>();
		if (isOutputConnection) {
			// output schema
			connectors.add(FLOW_CONNECTOR);
		}
		return connectors;
	}

}
