package org.talend.components.mongodb;

import org.talend.components.api.component.Connector;
import org.talend.components.api.component.PropertyPathConnector;
import org.talend.components.api.properties.ConnectionPropertiesProvider;
import org.talend.components.common.FixedConnectorsComponentProperties;
import org.talend.daikon.properties.presentation.Form;

import java.util.Collections;
import java.util.Set;

/**
 * Created by slemoing on 8/2/2016.
 */
public class MongoDBIOBasedProperties extends FixedConnectorsComponentProperties implements ConnectionPropertiesProvider<MongoDBConnectionProperties>{
    /**
     * FixedSchemaComponentProperties constructor comment.
     *
     * @param name
     */
    public MongoDBIOBasedProperties(String name) {
        super(name);
    }

    public MongoDBConnectionProperties connectionProperties = new MongoDBConnectionProperties("connectionProperties");

    public MongoDBSchemaProperties schemaProperties = new MongoDBSchemaProperties("schemaProperties", connectionProperties);

    protected transient PropertyPathConnector MAIN_CONNECTOR = new PropertyPathConnector(Connector.MAIN_NAME, "schemaProperties.main");

    public MongoDBSchemaProperties getSchemaProperties() {
        return schemaProperties;
    }


    @Override
    public void setupLayout() {
        super.setupLayout();
        Form mainForm = new Form(this, Form.MAIN);
        mainForm.addRow(connectionProperties.getForm(Form.REFERENCE));
        mainForm.addRow(schemaProperties.getForm(Form.REFERENCE));
    }

    @Override
    protected Set<PropertyPathConnector> getAllSchemaPropertiesConnectors(boolean isOutputConnection) {
        if (isOutputConnection) {
            return Collections.singleton(MAIN_CONNECTOR);
        } else {
            return Collections.EMPTY_SET;
        }
    }

    @Override
    public void setConnectionProperties(MongoDBConnectionProperties props) {

    }

    @Override
    public MongoDBConnectionProperties getConnectionProperties() {
        return connectionProperties;
    }
}
