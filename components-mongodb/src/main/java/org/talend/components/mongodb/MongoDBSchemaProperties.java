package org.talend.components.mongodb;

import org.talend.components.api.properties.ComponentPropertiesImpl;
import org.talend.components.api.properties.ConnectionPropertiesProvider;
import org.talend.components.common.SchemaProperties;
import org.talend.components.mongodb.runtime.MongoDBSourceOrSink;
import org.talend.daikon.NamedThing;
import org.talend.daikon.properties.ValidationResult;
import org.talend.daikon.properties.presentation.Form;
import org.talend.daikon.properties.presentation.Widget;
import org.talend.daikon.properties.property.Property;

import java.io.IOException;
import java.util.List;

import static org.talend.daikon.properties.property.PropertyFactory.newString;

/**
 * Created by slemoing on 8/2/2016.
 */
public class MongoDBSchemaProperties extends ComponentPropertiesImpl implements
        ConnectionPropertiesProvider<MongoDBConnectionProperties> {
    /**
     * named constructor to be used is these properties are nested in other properties. Do not subclass this method for
     * initialization, use {@link #init()} instead.
     *
     * @param name
     */
    public MongoDBSchemaProperties(String name,MongoDBConnectionProperties connectionProperties) {
        super(name);
        this.connectionProperties = connectionProperties;
    }

    public MongoDBConnectionProperties connectionProperties = new MongoDBConnectionProperties("connectionProperties");

    public Property<String> collection = newString("collection");
    public SchemaProperties main = new SchemaProperties("main");

    @Override
    public void setupLayout() {
        super.setupLayout();
        Form schemaForm = new Form(this, Form.MAIN);
        schemaForm.addRow(Widget.widget(collection).setWidgetType(Widget.NAME_SELECTION_AREA_WIDGET_TYPE));
        schemaForm.addRow(main.getForm(Form.MAIN));//add this line for dataset json
        refreshLayout(schemaForm);//FIXME why need to invoke refreshLayout here? refer to SalesforceModuleProperties

        Form schemaRefForm = new Form(this, Form.REFERENCE);
        schemaForm.addRow(Widget.widget(collection).setWidgetType(Widget.NAME_SELECTION_AREA_WIDGET_TYPE));
        schemaRefForm.addRow(main.getForm(Form.REFERENCE));//FIXME why need schema ref form here but don't need schema main form above
        refreshLayout(schemaRefForm);
    }

    private MongoDBSourceOrSink getMongoDBSourceOrSink() {
        // TODO(rskraba): redirect to the runtime to actually load.
        try {
            return (MongoDBSourceOrSink) Class.forName("org.talend.components.mongodb.runtime_3_0.MongoDBSourceOrSink").newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
    /*
        List all potential databases from host / port
    */
    public ValidationResult beforeDatabases(){
        MongoDBSourceOrSink mongoDBSourceOrSink = getMongoDBSourceOrSink();
        mongoDBSourceOrSink.initialize(null, connectionProperties);
        // TODO get list of mongodb collections
        try {
        List<String> databaseNames = mongoDBSourceOrSink.getDatabaseNames(null);
        connectionProperties.database.setPossibleValues(databaseNames);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ValidationResult.OK;
    }

    public ValidationResult beforeCollection(){
        try {
        MongoDBSourceOrSink mongoDBSourceOrSink = getMongoDBSourceOrSink();
        mongoDBSourceOrSink.initialize(null, connectionProperties);
        // TODO get list of mongodb collections
        List<String> collectionNames = mongoDBSourceOrSink.getCollectionsName(null,connectionProperties.database.getValue());
        collection.setPossibleValues(collectionNames);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ValidationResult.OK;
    }

    @Override
    public void setConnectionProperties(MongoDBConnectionProperties props) {
        connectionProperties = props;
    }

    @Override
    public MongoDBConnectionProperties getConnectionProperties() {
        return connectionProperties;
    }
}
