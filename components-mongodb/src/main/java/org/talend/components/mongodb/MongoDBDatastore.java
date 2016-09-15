package org.talend.components.mongodb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.talend.components.api.Constants;
import org.talend.components.api.component.AbstractDatastoreDefinition;
import org.talend.components.api.component.DatastoreDefinition;
import org.talend.components.api.properties.ComponentProperties;
import org.talend.daikon.properties.Properties;

import aQute.bnd.annotation.component.Component;

@Component(name = Constants.DATASTORE_BEAN_PREFIX + MongoDBDatastore.DATASTORE_NAME, provide = DatastoreDefinition.class)
public class MongoDBDatastore extends AbstractDatastoreDefinition {

    private static final Logger LOG = LoggerFactory.getLogger(MongoDBDatastore.class);

    public static final String DATASTORE_NAME = "MongoDBDatastore"; //$NON-NLS-1$

    public MongoDBDatastore() {
        super(DATASTORE_NAME);
    }

    public MongoDBDatastore(String datastoreName) {
        super(datastoreName);
    }

    @Override
    public Class<? extends ComponentProperties> getPropertyClass() {
        return MongoDBConnectionProperties.class;
    }

    @Override
    public String[] getFamilies() {
        return new String[]{ "Databases/MongoDB", "Big Data/MongoDB"}; //$NON-NLS-1$  //$NON-NLS-2$
    }

    @Override
    public String[] getDatasets() {
        return new String[]{ new MongoDBDataset().getName()};
    }

    @Override
    public String getJSONSchema() {
        return null;
    }

    @Override
    public String getMavenGroupId() {
        return "org.talend.components"; //$NON-NLS-1$
    }

    @Override
    public String getMavenArtifactId() {
        return "component-mongodb"; //$NON-NLS-1$
    }

    @Override
    public Boolean doValidate(String name, Properties props) {
        return true;
    }
}
