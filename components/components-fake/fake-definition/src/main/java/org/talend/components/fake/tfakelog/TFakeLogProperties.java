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
package org.talend.components.fake.tfakelog;

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

public class TFakeLogProperties extends FixedConnectorsComponentProperties {

	public TFakeLogProperties(String name) {
		super(name);
	}

	// input schema
	public transient PropertyPathConnector MAIN_CONNECTOR = new PropertyPathConnector(Connector.MAIN_NAME, "main");

	public SchemaProperties main = new SchemaProperties("main") ;

	@Override
	public void setupLayout() {
		super.setupLayout();
		Form mainForm = new Form(this, Form.MAIN);
		mainForm.addRow(main.getForm(Form.REFERENCE));
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
        main.schema.setValue(standardSchema);
	}

	@Override
	public void refreshLayout(Form form) {
		super.refreshLayout(form);
	}

	@Override
	protected Set<PropertyPathConnector> getAllSchemaPropertiesConnectors(boolean isOutputConnection) {
		HashSet<PropertyPathConnector> connectors = new HashSet<PropertyPathConnector>();
		if (!isOutputConnection) {
			// input schema
			connectors.add(MAIN_CONNECTOR);
		}
		return connectors;
	}

}
