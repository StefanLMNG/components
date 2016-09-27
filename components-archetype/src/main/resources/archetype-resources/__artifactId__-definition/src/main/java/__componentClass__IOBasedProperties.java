package ${package};

import ${package}.api.component.Connector;
import ${package}.api.component.PropertyPathConnector;
import ${package}.api.properties.ConnectionPropertiesProvider;
import ${package}.api.properties.SchemaPropertiesProvider;
import ${package}.common.FixedConnectorsComponentProperties;
import org.talend.daikon.properties.presentation.Form;

import java.util.Collections;
import java.util.Set;

public class ${componentClass}IOBasedProperties extends FixedConnectorsComponentProperties implements
        ConnectionPropertiesProvider<${componentClass}ConnectionProperties>,
        SchemaPropertiesProvider<${componentClass}SchemaProperties> {
    /**
     * named constructor to be used is these properties are nested in other properties. Do not subclass this method for
     * initialization, use {@link #init()} instead.
     *
     * @param name
     */
    public ${componentClass}IOBasedProperties(String name) {
        super(name);
        schemaProperties.setConnectionProperties(connectionProperties);
    }

    //TODO(bchen) want it private, but can't
    public ${componentClass}ConnectionProperties connectionProperties = new ${componentClass}ConnectionProperties("connectionProperties");
    public ${componentClass}SchemaProperties schemaProperties = new ${componentClass}SchemaProperties("schemaProperties");
    
    protected transient PropertyPathConnector MAIN_CONNECTOR = new PropertyPathConnector(Connector.MAIN_NAME, "schemaProperties.main");

    @Override
    public void setSchemaProperties(${componentClass}SchemaProperties props) {

    }

    @Override
    public ${componentClass}SchemaProperties getSchemaProperties() {
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
    public ${componentClass}ConnectionProperties getConnectionProperties() {
        return connectionProperties;
    }

    @Override
    public void setConnectionProperties(${componentClass}ConnectionProperties props){

    }

    @Override
    protected Set<PropertyPathConnector> getAllSchemaPropertiesConnectors(boolean isOutputConnection) {
        if (isOutputConnection) {
            return Collections.singleton(MAIN_CONNECTOR);
        } else {
            return Collections.EMPTY_SET;
        }
    }
}
