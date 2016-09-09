package org.talend.components.mongodb;

import org.talend.components.api.properties.ComponentPropertiesImpl;
import org.talend.daikon.properties.presentation.Form;
import org.talend.daikon.properties.presentation.Widget;

/**
 * Created by slemoing on 8/2/2016.
 */
public class MongoDBSchemaProperties extends ComponentPropertiesImpl {
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

    private MongoDBConnectionProperties connectionProperties;

    @Override
    public void setupLayout() {
        super.setupLayout();
        Form schemaForm = new Form(this, Form.MAIN);

        refreshLayout(schemaForm);//FIXME why need to invoke refreshLayout here? refer to SalesforceModuleProperties

        Form schemaRefForm = new Form(this, Form.REFERENCE);
        refreshLayout(schemaRefForm);
    }
}
